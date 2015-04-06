import controller
from entities import *
import image_store
import pygame
import random
from save_load import *
import sys
from worldmodel import *
from worldview import *

RUN_AFTER_LOAD = True

IMAGE_LIST_FILE_NAME = 'imagelist'
WORLD_FILE = 'gaia.sav'

WORLD_WIDTH_SCALE = 2
WORLD_HEIGHT_SCALE = 2

SCREEN_WIDTH = 640
SCREEN_HEIGHT = 480
TILE_WIDTH = 32
TILE_HEIGHT = 32


def create_default_background(img):
   return Background(image_store.DEFAULT_IMAGE_NAME, img)


def main_load_world(world, i_store, filename):
   with open(filename, 'r') as file:
      world.load_world(i_store, file, RUN_AFTER_LOAD)


def main():
   random.seed()
   pygame.init()
   screen = pygame.display.set_mode((SCREEN_WIDTH, SCREEN_HEIGHT))
   i_store = image_store.load_images(IMAGE_LIST_FILE_NAME,
      TILE_WIDTH, TILE_HEIGHT)

   num_cols = SCREEN_WIDTH // TILE_WIDTH * WORLD_WIDTH_SCALE
   num_rows = SCREEN_HEIGHT // TILE_HEIGHT * WORLD_HEIGHT_SCALE

   default_background = create_default_background(
      image_store.get_images(i_store, image_store.DEFAULT_IMAGE_NAME))

   world = WorldModel(num_rows, num_cols, default_background)
   view = WorldView(SCREEN_WIDTH // TILE_WIDTH,
      SCREEN_HEIGHT // TILE_HEIGHT, screen, world, TILE_WIDTH, TILE_HEIGHT)

   main_load_world(world, i_store, WORLD_FILE)

   view.update_view()

   controller.activity_loop(view, world)


if __name__ == '__main__':
   main()
