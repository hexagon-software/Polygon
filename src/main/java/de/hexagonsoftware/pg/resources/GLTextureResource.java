package de.hexagonsoftware.pg.resources;

import java.awt.image.BufferedImage;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import de.hexagonsoftware.pg.util.ImageLoader;
import de.hexagonsoftware.pg.util.PGGraphicsUtil;

public class GLTextureResource implements IResource {
    private Texture texture;

    public GLTextureResource(String texture, Class<?> CLASS, GL gl) {
        BufferedImage in = ImageLoader.loadImage(texture, CLASS);

        in = PGGraphicsUtil.flipHorizontal(in);
        in = PGGraphicsUtil.flipVertical(in);

        TextureData data = AWTTextureIO.newTextureData(gl.getGLProfile(), in, false);
        this.texture = TextureIO.newTexture(gl, data);
        this.texture.bind(gl);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
    }

    public Texture get() {
        return texture;
    }
}
