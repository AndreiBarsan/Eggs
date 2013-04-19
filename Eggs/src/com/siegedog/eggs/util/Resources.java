package com.siegedog.eggs.util;


import java.util.HashMap;

import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.siegedog.eggs.AnimatedSprite;

// This guy over here handles sounds and sprites and boxes
// Originally written for the Minogear game, LD24 
public class Resources {
	
	private static final String ASS_FOLDER = "../Eggs/res";
	
	String sfxRoot = ASS_FOLDER + "/sfx/";
	String texRoot = ASS_FOLDER + "/img/";
	String effectRoot = ASS_FOLDER + "/particleEffects/";
	String effectImgRoot = ASS_FOLDER + "/img/particles/";
	String shaderRoot = ASS_FOLDER + "/shaders/";
	String fontRoot = ASS_FOLDER + "/font/";
	
	AssetManager assetManager = new AssetManager();
	private boolean done = false;
	private boolean error = false;
	
	// Callback for when all tasks are ready
	private Runnable onLoadFinish;
	private Runnable onError;
	
	HashMap<String, TextureRegion> textureRegions = new HashMap<String, TextureRegion>(10);
	HashMap<String, PolygonShape> polys = new HashMap<String, PolygonShape>(10);
	HashMap<String, Sprite> sprites = new HashMap<String, Sprite>(32);
	HashMap<String, ParticleEffect> particleEffects = new HashMap<String, ParticleEffect>(10);
	//HashMap<String, ShaderProgram> shaders = new HashMap<String, ShaderProgram>(10);
	
	// Save friendly names for the loaded assets
	HashMap<String, String> tNames = new HashMap<String, String>(10);
	HashMap<String, String> sNames = new HashMap<String, String>(10);
	
	public Resources () {
		this(new InternalFileHandleResolver());
	}
	
	public Resources(FileHandleResolver resolver) {
		assetManager.setErrorListener(new AssetErrorListener() {
			@SuppressWarnings("rawtypes")
			@Override
			public void error(String fileName, Class type, Throwable throwable) {
				error = true;
				String source = "Asset load fail: " + fileName + "\n" + throwable;
				if(onError != null) {
					// Optional callback when loading fails
					Log.D(source);
					onError.run();
				} else {
					Log.E(source);
				}
			}
		});
	}
	
	public void update(float delta) {
		boolean old = done;
		done = assetManager.update();

		if (!old && done) {
			System.out.println("Resources are done loading.");
			if(error) {
				Log.D("Skipping advanced resource processing due to an earlier resource loading error.");
				return;
			}

			if(assetManager.containsAsset(TextureAtlas.class)) {
				// Prepare sprites from the atlas
				TextureAtlas atlas = assetManager.get(texRoot + "pack", TextureAtlas.class);
				for(AtlasRegion tr : atlas.getRegions()) {
					sprites.put(tr.name, new Sprite(tr));
				}
			}
			
			if(onLoadFinish != null) {
				onLoadFinish.run();
			}
		}
		/*
		if(!done)
			System.out.println(assetManager.getQueuedAssets() + " assets queued.");
		*/
		if (old && !done) {
			//System.out.println("Starting to load resources.");
			
		}
	}
	
	public Resources onLoadFinish(Runnable action) {
		onLoadFinish = action;
		return this;
	}
	
	public Resources onError(Runnable runnable) {
		onError = runnable;
		return this;
	}
	
	public boolean done() {
		return done;
	}

	public boolean isLoaded(String file) {
		return assetManager.isLoaded("assets/" + file);
	}
	
	public Resources loadTex(String fileName, String name) {
		assetManager.load(texRoot + fileName, Texture.class);
		tNames.put(name, texRoot + fileName);		
		return this;
	}
	
	// Loads the central tex. atlas
	// Note: prefer using this instead of custom-made texture sheets
	public Resources loadAtlas() {
		assetManager.load(texRoot + "pack", TextureAtlas.class);
		return this;
	}

	// TODO: more standard
	public Resources loadSfx(String fileName, String name) {
		// FileHandle file = new FileHandle(sfxRoot + "Powerup21" + extension);
		assetManager.load(sfxRoot + fileName, Sound.class);
		sNames.put(name, sfxRoot + fileName); 
		return this;
	}	
	
	public Resources loadTR(TextureRegion data, String name) {
		textureRegions.put(name, data);
		return this;
	}
	
	public Resources loadShader(String name) {
		assetManager.load(shaderRoot + name, ShaderProgram.class);
		return this;
	}
	
	// TODO: make loader
	public Resources loadPolys(String fileName) {
		FileHandle file = new FileHandle(fileName);
		String data = file.readString("UTF-8");
		String polyName;
		
		for(String line : data.split("\n")) {
			int i = line.indexOf(' ');
			polyName = line.substring(0, i);
			line = line.substring(i + 1);
			
			String[] pList = line.split(",");
			Vector2[] vertices = new Vector2[pList.length];
			
			if(pList.length > 1) {
				int k = 0;
				for(String pointStr : pList) {
					String[] result = new String[2];
					result = pointStr.trim().split(" ");
					vertices[k++] = new Vector2(Float.parseFloat(result[0]), Float.parseFloat(result[1]));
				}
				
				PolygonShape ps = new PolygonShape();
				ps.set(vertices);
				polys.put(polyName, ps);
			} else if(pList.length == 1) {
				PolygonShape ps = new PolygonShape();
				
				String[] result = pList[0].trim().split(" ");
				if(result.length == 1) {
					float l = Float.parseFloat(result[0]);
					ps.setAsBox(l, l);
				} else if(result.length == 2) {
					float w = Float.parseFloat(result[0]);
					float h = Float.parseFloat(result[1]);
					ps.setAsBox(w, h);
				} 
				
				polys.put(polyName, ps);
			} else {
				Log.D("Problem loading a poly...");
			}
		}
		
		return this;
	}
	
	public Resources loadPoly(PolygonShape shape, String name) {
		polys.put(name, shape);
		return this;
	}
	
	public Resources loadSprite(Sprite sprite, String name) {
		sprites.put(name, sprite);
		return this;
	}
	
	public Resources loadFont(String name) {
		assetManager.load(fontRoot + name + ".fnt", BitmapFont.class);
		return this;
	}
	
	public Resources loadParticleEffect(String fileName, String name) {
		ParticleEffect pe = new ParticleEffect();
		pe.load(new FileHandle(effectRoot + fileName), new FileHandle(effectImgRoot));
		particleEffects.put(name, pe);
		return this;
	}
	
	public Resources loadEffects() {
		FileHandle effectFolder = new FileHandle(effectRoot);
		if(!effectFolder.isDirectory()) {
			Log.E("Bad data folder.");
		}
		
		for(FileHandle file : effectFolder.list()) {
			if(!file.isDirectory())
				loadParticleEffect(file.name(), file.name());
		}
		
		return this;
	}
	
	public Resources loadSounds() {
		FileHandle sfxFolder = new FileHandle(sfxRoot);
		
		if(!sfxFolder.isDirectory()) {
			Log.E("Bad data folder.");
		}
		
		for(FileHandle file : sfxFolder.list()) {
			if(!file.isDirectory()) {
				loadSfx(file.name(), file.name());
				sNames.put(file.name().subSequence(0, file.name().lastIndexOf(".")).toString(), sfxRoot + file.name()); 
			}
		}
		
		
		
		return this;
	}
	
	public Resources createSprite(String spriteSheetName, int x0, int y0,
			int width, int height, String name) {
		return createSprites(spriteSheetName, x0, y0, width, height, new String[] { name });
	}
	
	
	/**
	 * Generates sprites from a sprite-sheet with a well-defined grid. Generates names.length
	 * sprites which automatically get inserted into the resource managers' sprite list. Starts
	 * at x0, y0 then keeps going right until all the names are used up. Upon reaching the right
	 * end of the texture, continues one row lower.
	 * 
	 * @param spriteSheetName The name of the <b>already loaded</b> texture to use
	 * 
	 * @note This call doesn't load any external data and should operate really fast. It's 
	 * 		common to call it in the Resource object's onLoadFinish callback.
	 */
	public Resources createSprites(String spriteSheetName, int x0, int y0,
			int width, int height, String[] names) {
		
		Texture sheet = tex(spriteSheetName);
		int sheetWidth = sheet.getWidth();
		int sprPerRow = (sheetWidth - x0) / width; 
		for(int i = 0; i < names.length; i++) {
			
			int xoffset = i % sprPerRow;
			int yoffset = i / sprPerRow;
			
			int sx = x0 + xoffset * width;
			int sy = y0 + yoffset * height; 
			TextureRegion t = new TextureRegion(sheet, sx, sy, width, height);
			
			Sprite aux = new Sprite(t);
			aux.setOrigin(width / 2, height / 2);
			sprites.put(names[i], aux);
		}
		
		return this;
	}
	
	public Resources createSprite(String srcName) {
		return createSprite(srcName, srcName);
	}
	
	public Resources createSprite(String srcName, String destName) {
		Sprite aux = new Sprite(tex(srcName));
		aux.setOrigin(aux.getWidth()/2, aux.getHeight() / 2);
		sprites.put(destName, aux);		
		return this;
	}
	
	// ACCESS
	public void play(String name) {
			(assetManager.get(sNames.get(name), Sound.class)).play();
	}
	
	public Texture tex(String name) {
		return assetManager.get(tNames.get(name), Texture.class);
	}
	
	public TextureRegion texReg(String name) {
		return textureRegions.get(name);
	}
	
	public PolygonShape poly(String name) {
		return polys.get(name);
	}
	
	public BitmapFont font(String name) {
		return assetManager.get(fontRoot + name + ".fnt", BitmapFont.class);
	}
	
	public ParticleEffect eff(String name) {
		return particleEffects.get(name);
	}
	
	public Sprite sprite(String name) {
		return sprites.get(name);
	}
	
	/**
	 * Just conveniently returns an animated sprite instead of a regular
	 * sprite.
	 */
	public AnimatedSprite animatedSprite(String name) {
		return new AnimatedSprite(sprites.get(name));
	}
	
	public TextureRegion spriteRegion(String name) {
		Sprite s = sprites.get(name);
		return new TextureRegion(s.getTexture(), s.getRegionX(), s.getRegionY(),
				s.getRegionWidth(), s.getRegionHeight());
	}
	
	public ShaderProgram shader(String name) {
		return assetManager.get(shaderRoot + name, ShaderProgram.class);
	}

	public void dispose() {
		assetManager.dispose();
		textureRegions.clear();	
		polys.clear();
	}
}
