import point
import worldmodel

PROPERTY_KEY = 0
BGND_KEY = 'background'
BGND_NUM_PROPERTIES = 4
BGND_NAME = 1
BGND_COL = 2
BGND_ROW = 3

MINER_KEY = 'miner'
MINER_NUM_PROPERTIES = 7
MINER_NAME = 1
MINER_LIMIT = 4
MINER_COL = 2
MINER_ROW = 3
MINER_RATE = 5
MINER_ANIMATION_RATE = 6

OBSTACLE_KEY = 'obstacle'
OBSTACLE_NUM_PROPERTIES = 4
OBSTACLE_NAME = 1
OBSTACLE_COL = 2
OBSTACLE_ROW = 3

ORE_KEY = 'ore'
ORE_NUM_PROPERTIES = 5
ORE_NAME = 1
ORE_COL = 2
ORE_ROW = 3
ORE_RATE = 4

SMITH_KEY = 'blacksmith'
SMITH_NUM_PROPERTIES = 7
SMITH_NAME = 1
SMITH_COL = 2
SMITH_ROW = 3
SMITH_LIMIT = 4
SMITH_RATE = 5
SMITH_REACH = 6

VEIN_KEY = 'vein'
VEIN_NUM_PROPERTIES = 6
VEIN_NAME = 1
VEIN_RATE = 4
VEIN_COL = 2
VEIN_ROW = 3
VEIN_REACH = 5
WORLD_FILE_NAME = 'gaia.sav'

BACKGROUND_TAGS = ['grass', 'rocks']

TIMER_FREQUENCY = 100

MINER_LIMIT = 2
MINER_RATE_MIN = 600
MINER_RATE_MAX = 1000
MINER_ANIMATION_RATE = 100
VEIN_RATE_MIN = 8000
VEIN_RATE_MAX = 17000
ORE_RATE_MIN = 20000
ORE_RATE_MAX = 30000
SMITH_LIMIT_MIN = 10
SMITH_LIMIT_MAX = 15
SMITH_RATE_MIN = 2000
SMITH_RATE_MAX = 4000

BLOB_RATE_SCALE = 4
BLOB_ANIMATION_RATE_SCALE = 50
BLOB_ANIMATION_MIN = 1
BLOB_ANIMATION_MAX = 3

ORE_CORRUPT_MIN = 20000
ORE_CORRUPT_MAX = 30000

QUAKE_STEPS = 10
QUAKE_DURATION = 1100
QUAKE_ANIMATION_RATE = 100

VEIN_SPAWN_DELAY = 500
VEIN_RATE_MIN = 8000
VEIN_RATE_MAX = 17000


#this is the parent class to all of the entities
class Entity(object):
   def __init__(self,name,imgs,position):
      self.name = name
      self.imgs = imgs 
      self.position = position
 
   def set_position(self,point):
      self.position = point
   def get_position(self):
      return self.position
   def get_images(self):
      return self.imgs
   def get_image(self):
      return self.imgs[self.current_img]
   def get_name(self):
      return self.name
class NonStaticEntity(Entity):
   def __init__(self,name,imgs,position,rate,resource_count,resource_limit,resource_distance,
                     animation_rate,current_img):
      super(NonStaticEntity,self).__init__(name,imgs,position)
      self.rate = rate
      self.resource_count = resource_count
      self.resource_limit = resource_limit
      self.resource_distance = resource_distance
      self.animation_rate = animation_rate
      self.current_img = current_img
   def get_rate(self):
      return self.rate
   def set_resource_count(self, n):
      self.resource_count = n
   def get_resource_count(self):
      return self.resource_count
   
   def get_resource_limit(self):
      return self.resource_limit
   def get_resource_distance(self):
      return self.resource_distance
   
   def get_animation_rate(self):
      return self.animation_rate

   def next_image(self):
      self.current_img = (self.current_img + 1) % len(self.imgs)


   def move_entity(self,world, pt):
      tiles = []
      if world.within_bounds(pt):
         old_pt = self.get_position()
         world.occupancy.set_cell(old_pt, None)
         tiles.append(old_pt)
         tiles.append(pt)
         self.set_position(pt)
      return tiles
   def blob_to_vein(self, world, vein):
      entity_pt = self.get_position()
      if not vein:
         return ([entity_pt], False)
      vein_pt = vein.get_position()
      if worldmodel.adjacent(entity_pt, vein_pt):
         world.remove_entity(vein)
         return ([vein_pt], True)
      else:
         new_pt = world.blob_next_position(entity_pt, vein_pt)
         old_entity = world.get_tile_occupant(new_pt)
         if isinstance(old_entity, Ore):
            world.remove_entity(old_entity)
         return (self.move_entity(world, new_pt), False)

#all the methods that schedule the entities 
#entity actions and obstacle enherit from it
class ScheduleEntity(NonStaticEntity):
   def __init__(self,name,imgs,position,rate,resource_count,resource_limit,
                     resource_distance, animation_rate, current_img):
      super(ScheduleEntity,self).__init__(name,imgs,position,rate,resource_count,
                           resource_limit,resource_distance,animation_rate,current_img)
   
   def schedule_entity(self,world, i_store):
      if isinstance(self, MinerNotFull):
         self.schedule_miner(world, 0, i_store)
      elif isinstance(self, Vein):
         self.schedule_vein(world, 0, i_store)
      elif isinstance(self, Ore):
         self.schedule_ore(world, 0, i_store)
   def schedule_vein(self, world, ticks, i_store):
      self.schedule_action(world, self.create_vein_action(world, 
          i_store),ticks + self.get_rate())

   def schedule_quake(self, world, ticks):
      world.schedule_animation(self, QUAKE_STEPS) 
      self.schedule_action(world, self.create_entity_death_action(world),
          ticks + QUAKE_DURATION)

   def schedule_ore(self, world, ticks, i_store):
      self.schedule_action(world,self.create_ore_transform_action(world, 
          i_store),ticks + self.get_rate())

   def schedule_miner(self, world, ticks, i_store):
      self.schedule_action(world, self.create_miner_action(world, 
          i_store),ticks + self.get_rate())
      world.schedule_animation(self)

   def schedule_blob(self, world, ticks, i_store):
      self.schedule_action(world, self.create_ore_blob_action(world, 
          i_store),ticks + self.get_rate())
      world.schedule_animation(self)


#all the action methods for all the entities
class EntityActions(ScheduleEntity):
   def __init__(self,name,imgs,position,rate,resource_count,resource_limit,
                     resource_distance, animation_rate, current_img, pending_actions):
      super(EntityActions,self).__init__(name,imgs,position,rate,resource_count,resource_limit,
                     resource_distance, animation_rate, current_img)
      self.pending_actions = pending_actions
   def remove_pending_action(self, action):
      if hasattr(self, "pending_actions"):
         self.pending_actions.remove(action)
   def add_pending_action(self, action):
      if hasattr(self, "pending_actions"):
         self.pending_actions.append(action)
   def get_pending_actions(self):
      if hasattr(self, "pending_actions"):
         return self.pending_actions
      else:
         return []
   def clear_pending_actions(self):
      if hasattr(self, "pending_actions"):
         self.pending_actions = []   
   def schedule_action(self, world, action, time):
      self.add_pending_action(action)
      world.new_schedule_action(action, time)

   def create_ore_blob_action(self, world, i_store):
      def action(current_ticks):
         self.remove_pending_action(action)

         entity_pt = self.get_position()
         vein = world.find_nearest(entity_pt, Vein)
         (tiles, found) = self.blob_to_vein(world, vein)

         next_time = current_ticks + self.get_rate()
         if found:
            quake = world.create_quake(tiles[0], current_ticks, i_store)
            world.new_add_entity(quake)
            next_time = current_ticks + self.get_rate() * 2

         self.schedule_action(world,self.create_ore_blob_action(
             world, i_store),next_time)

         return tiles
      return action

   def create_vein_action(self, world, i_store):
      def action(current_ticks):
         self.remove_pending_action(action)

         open_pt = world.find_open_around(self.get_position(),
             self.get_resource_distance())
         if open_pt:
            ore = world.create_ore("ore - " + self.get_name() + " - " + 
                str(current_ticks),open_pt, current_ticks, i_store)
            world.new_add_entity(self)
            tiles = [open_pt]
         else:
            tiles = []

         self.schedule_action(world,self.create_vein_action(
             world, i_store),current_ticks + self.get_rate())
         return tiles
      return action
   def create_animation_action(self, world, repeat_count):
      def action(current_ticks):
         self.remove_pending_action(action)

         self.next_image()

         if repeat_count != 1:
            self.schedule_action(world,self.create_animation_action(
                world, max(repeat_count - 1, 0)),current_ticks + 
                    self.get_animation_rate())

         return [self.get_position()]
      return action
   def create_entity_death_action(self, world):
      def action(current_ticks):
         self.remove_pending_action(action)
         pt = self.get_position()
         world.remove_entity(self)
         return [pt]
      return action
   def create_ore_transform_action(self, world, i_store):
      def action(current_ticks):
         self.remove_pending_action(action)
         blob = world.create_blob(self.get_name() + 
             " -- blob",self.get_position(),self.get_rate() 
                 // BLOB_RATE_SCALE,current_ticks, i_store)

         world.remove_entity(self)
         world.new_add_entity(blob)

         return [blob.get_position()]
      return action
   def create_miner_action(self, world, image_store):
      if isinstance(self, MinerNotFull):
         return self.create_miner_not_full_action(world, image_store)
      else:
         return self.create_miner_full_action(world, image_store)

   def create_miner_not_full_action(self, world, i_store):
      def action(current_ticks):
         self.remove_pending_action(action)

         entity_pt = self.get_position()
         ore = world.find_nearest(entity_pt, Ore)
         (tiles, found) = self.miner_to_ore(world, ore)

         new_entity = self
         if found:
            new_entity = self.try_transform_miner(world,
               self.try_transform_miner_not_full)

         new_entity.schedule_action(world,
            new_entity.create_miner_action(world, i_store),
            current_ticks + new_entity.get_rate())
         return tiles
      return action


   def create_miner_full_action(self, world, i_store):
      def action(current_ticks):
         self.remove_pending_action(action)

         entity_pt = self.get_position()
         smith = world.find_nearest(entity_pt, Blacksmith)
         (tiles, found) = self.miner_to_smith(world, smith)

         new_entity = self
         if found:
            new_entity = self.try_transform_miner(world,
               self.try_transform_miner_full)

         new_entity.schedule_action(world,
            new_entity.create_miner_action(world, i_store),
            current_ticks + new_entity.get_rate())
         return tiles
      return action


#has everything that relates to the two miner classes not relating to actions
#because it inherits entity actions which has all the actions
#it inherits from the entity actions superclass
class MinerEntity(EntityActions):
   def __init__(self,name,imgs,position,rate,resource_count,resource_limit,
                     resource_distance, animation_rate, current_img, pending_actions):
      super(MinerEntity,self).__init__(name,imgs,position,rate,resource_count,resource_limit,
                     resource_distance, animation_rate, current_img,pending_actions)

   def try_transform_miner_not_full(self,world):
      if self.resource_count < self.resource_limit:
         return self
      else:
         new_entity = MinerFull(
            self.get_name(), self.get_resource_limit(),
            self.get_position(), self.get_rate(),
            self.get_images(), self.get_animation_rate())
         return new_entity
   def try_transform_miner_full(self, world):
      new_entity = MinerNotFull(
        self.get_name(), self.get_resource_limit(),
        self.get_position(), self.get_rate(),
        self.get_images(), self.get_animation_rate())

      return new_entity

   def miner_to_ore(self, world, ore):
      entity_pt = self.get_position()
      if not ore:
         return ([entity_pt], False)
      ore_pt = ore.get_position()
      if adjacent(entity_pt, ore_pt):
         self.set_resource_count(
            1 + entity.get_resource_count())
         world.remove_entity(ore)
         return ([ore_pt], True)
      else:
         new_pt = world.next_position(entity_pt, ore_pt)
         return (self.move_entity(world, new_pt), False)


   def miner_to_smith(self, world, smith):
      entity_pt = self.get_position()
      if not smith:
         return ([entity_pt], False)
      smith_pt = smith.get_position()
      if adjacent(entity_pt, smith_pt):
         smith.set_resource_count(
            smith.get_resource_count() +
            self.get_resource_count())
         self.set_resource_count(0)
         return ([], True)
      else:
         new_pt = world.next_position(entity_pt, smith_pt)
         return (self.move_entity(world, new_pt), False)
   def try_transform_miner(self, world, transform):
      new_entity = transform(self)
      if entity != new_entity:
         world.clear_pending_actions(self)
         world.remove_entity_at(self.get_position())
         world.new_add_entity(new_entity)
         self.schedule_animation(new_entity)

      return new_entity

#small enough - not enheriting the superclass entity   
class Background():
   def __init__(self,name,imgs):
      self.name = name
      self.imgs = imgs
      self.current_img = 0
   def get_images(self):
      return self.imgs
   def get_image(self):
      return self.imgs[self.current_img]
   def next_image(self):
      self.current_img = (self.current_img + 1) % len(self.imgs)
   def get_name(self):
      return self.name
      
  
#enherits from the miner entity class, which is for the two miner classes
class MinerNotFull(MinerEntity):
   def __init__(self,name,resource_limit, position, rate,
       imgs,animation_rate):

      super(MinerNotFull,self).__init__(name,imgs,position,rate,0,resource_limit,
                                 0,animation_rate,0,[])

      self.current_img = 0
      self.resource_count = 0
      self.pending_actions = []

#enherits from the miner entity class
class MinerFull(MinerEntity):
   def __init__(self, name, resource_limit, position, rate, imgs,
      animation_rate):
      super(MinerFull,self).__init__(name,imgs,position,rate,resource_limit,resource_limit,
                                 0,animation_rate,0,0,[])
  
      self.current_img = 0
      self.resource_count = resource_limit
      self.pending_actions = []


class Vein(EntityActions):
   def __init__(self, name, rate, position, imgs, resource_distance=1):
      super(Vein,self).__init__(name,imgs,position,rate,0,0,resource_distance,0,
                           0,[])

      self.rate = rate
      self.current_img = 0
      self.resource_distance = resource_distance
      self.pending_actions = []


class Ore(EntityActions):
   def __init__(self, name, position, imgs, rate=5000):
      super(Ore,self).__init__(name,imgs,position,rate,0,0,0,0,0,[])
  
      self.current_img = 0
      self.rate = rate
      self.pending_actions = []


class Blacksmith(EntityActions):
   def __init__(self, name, position, imgs, resource_limit, rate,
      resource_distance=1):
      super(Blacksmith,self).__init__(name,imgs,position,rate,0,resource_limit,resource_distance,0,0,[])
     
      self.current_img = 0
      self.resource_limit = resource_limit
      self.resource_count = 0
      self.rate = rate
      self.resource_distance = resource_distance
      self.pending_actions = []


class Obstacle(EntityActions):
   def __init__(self, name, position, imgs):
      super(Obstacle,self).__init__(name,imgs,position,0,0,0,0,0,0,0)
      
      self.current_img = 0
 
class OreBlob(EntityActions):
   def __init__(self, name, position, rate, imgs, animation_rate):
      super(OreBlob,self).__init__(name,imgs,position,rate,0,0,0,animation_rate,0,[])
   
      self.rate = rate
      self.current_img = 0
      self.animation_rate = animation_rate
      self.pending_actions = []

class Quake(EntityActions):
   def __init__(self, name, position, imgs, animation_rate):
      super(Quake,self).__init__(name,imgs,position,rate,0,0,0,animation_rate,0,[])
   
      self.current_img = 0
      self.animation_rate = animation_rate
      self.pending_actions = []

# This is a less than pleasant file format, but structured based on
# material covered in course.  Something like JSON would be a
# significant improvement.
def entity_string(entity):
   if isinstance(entity, MinerNotFull):
      return ' '.join(['miner', entity.name, str(entity.position.x),
         str(entity.position.y), str(entity.resource_limit),
         str(entity.rate), str(entity.animation_rate)])
   elif isinstance(entity, Vein):
      return ' '.join(['vein', entity.name, str(entity.position.x),
         str(entity.position.y), str(entity.rate),
         str(entity.resource_distance)])
   elif isinstance(entity, Ore):
      return ' '.join(['ore', entity.name, str(entity.position.x),
         str(entity.position.y), str(entity.rate)])
   elif isinstance(entity, Blacksmith):
      return ' '.join(['blacksmith', entity.name, str(entity.position.x),
         str(entity.position.y), str(entity.resource_limit),
         str(entity.rate), str(entity.resource_distance)])
   elif isinstance(entity, Obstacle):
      return ' '.join(['obstacle', entity.name, str(entity.position.x),
         str(entity.position.y)])
   else:
      return 'unknown'

