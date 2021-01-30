# Polygon

## Quick Intro
The Polygon Engine is a simple 2D Game Engine, focused on
the creation of simple 2D games and supports AWT Graphics
and OpenGL. However, development is focused on OpenGL.

### Creating a Game-Base
**The Main Class** <br />
Your Main-Class should only contain the main-function which launches
the Game.
```java
public static void main(String[] args) {
    // The first argument is your Instance of the IGameInit interface, 
    // the second one the class, which to use for loading the Properties specified in the last argument
    Polygon pg = Launcher.launch(new IGameInit() {...}, Main.class, "/yourengineproperties.properties")
}
```

**The Game Class** <br />
The Game-Class is the "Control Centre" of your game, in this class you
add/instantiate your Game-Objects, set your HUDs, add your Key-Bindings
and contains and update and a render method for your needs.

**Game Objects** <br />
Game-Objects are pretty self-explanatory, but here is some important information:
1. Coordinates for Game-Objects are in World-Units, which are 32\*widthScaleFactor x 32\*heightScaleFactor
2. On Creation, the Game-Object requires an Instance of an GameObjectRenderer used for rendering

## What does this engine do and what not?
Polygon aims at making Java Game creation easy and fast, it is expandable
and leaves much room for customisation, it takes the task of rendering, sound
playback and resource loading from you.
What it does **not** do is physics, this is left up to implement yourself,
however this should be easy, since Polygon Contains JBox2D and is compatible
with it.
However, Polygon will eventually have native physics support.

## License
The Polygon-Engine is licensed under the 3-Clause BSD new or revised license,
a copy can be found in the LICENSE.txt file. Alternatively you can find it
on the [official github repository](https://www.github.com/hexagon-software/Polygon).