import entities
import image_store
import keys
import mouse_buttons
import point
import pygame
import random
import save_load
import worldview
from worldmodel import *

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


def mouse_to_tile(pos, tile_width, tile_height):
   return point.Point(pos[0] // tile_width, pos[1] // tile_height)

def on_keydown(event, world, entity_select, i_store):
   x_delta = 0
   y_delta = 0
   if event.key == pygame.K_UP: y_delta -= 1
   if event.key == pygame.K_DOWN: y_delta += 1
   if event.key == pygame.K_LEFT: x_delta -= 1
   if event.key == pygame.K_RIGHT: x_delta += 1
   elif event.key in keys.ENTITY_KEYS:
      entity_select = keys.ENTITY_KEYS[event.key]
   elif event.key == keys.SAVE_KEY: world.save_world(WORLD_FILE_NAME)
   elif event.key == keys.LOAD_KEY: world.load_world(i_store, WORLD_FILE_NAME)

   return ((x_delta, y_delta), entity_select)


def is_background_tile(entity_select):
   return entity_select in BACKGROUND_TAGS



