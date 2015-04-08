import entities
import image_store
import keys
import mouse_buttons
import point
import pygame
import random
import save_load
import worldview
import worldmodel

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

class Point:
   def __init__(self, x, y):
      self.x = x
      self.y = y
   def create_new_entity(self, entity_select, i_store):
      name = entity_select + '_' + str(self.x) + '_' + str(self.y)
      images = image_store.get_images(i_store, entity_select)
      if entity_select == 'obstacle':
         return entities.Obstacle(name, self, images)
      elif entity_select == 'miner':
         return entities.MinerNotFull(name, MINER_LIMIT,self,
             random.randint(MINER_RATE_MIN, MINER_RATE_MAX),
                 images, MINER_ANIMATION_RATE)
      elif entity_select == 'vein':
         return entities.Vein(name,
             random.randint(VEIN_RATE_MIN, VEIN_RATE_MAX), self, images)
      elif entity_select == 'ore':
         return entities.Ore(name, self, images,
             random.randint(ORE_RATE_MIN, ORE_RATE_MAX))
      elif entity_select == 'blacksmith':
         return entities.Blacksmith(name, self, images,
             random.randint(SMITH_LIMIT_MIN, SMITH_LIMIT_MAX),
                 random.randint(SMITH_RATE_MIN, SMITH_RATE_MAX))
      else:
         return None

  
