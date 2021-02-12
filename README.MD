# Polygon
*Polygon is a Java/OpenGL Game Engine, based of the Hexagon Engine (discontinued)*

## About
Polygon is a Game Engine, aiming to be able to be used nearly completly programitcally.
Everything except Level-Creation should be accomplished through code. <br>
It currently uses JOGL as its OpenGL binding, it provides an Graphics Class similar
to the awt.Graphcis class

## Example
*This section gives a brief introduction on how to setup the engine in your code, a more consice introduction will be added in the repo
wiki.*

Your Main Method
```java
public static void main(String[] args) {
    // The first argument should be a path to your engine 
    // settings, the second should be a class which will be used 
    // by the engine for loading resources from jar files.
    Polygon pg  =new Polygon("/your_engine_settings.properties", ClassForLoadingResources.class);

    // Start the engine
    pg.start(new YourGameClass());
}
```

Your Game Class
```java
public class YourGameClass implements IGame {
    @Override
	public void update() {
        // You dont need to put anything in this function
	}

	@Override
	public void render(GLGraphics g) {
        // Used for rendering, will always be rendered
        // ontop of everything. (You dont need to put
        // anything in this function)
    }

	@Override
	public void start() {
        // Add your game objects here
        // Load your level here
	}
}
```

## Installation
The engine is currently **not** deployed via maven. You can simply
download the source code and copy it into your project, for easy
modification. It is however recommended to download a binary (if avaliable) for usage of the engine.

