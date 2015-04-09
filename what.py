def add_entity(world, entity):
   pt = entity.get_position()
   if world.within_bounds(pt):
      old_entity = world.occupancy.get_cell(pt)
      if old_entity != None:
         clear_pending_actions(old_entity)
      world.occupancy.set_cell(pt, entity)
      world.entities.append(entity)
 
def add_entity(self, properties, i_store, run):
      new_entity = create_from_properties(properties, i_store)
      if new_entity:
         worldmodel.add_entity(self, new_entity)
         if run:
            schedule_entity(self, new_entity, i_store)

NEW
