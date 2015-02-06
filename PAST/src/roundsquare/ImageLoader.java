package roundsquare;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;

/**ImageLoader.java
 * 
 * Written with help from tutorial ("Killer Game Programming in Java")
 * Loads images, and stores them in a String/ArrayList<BufferedImage> HashMap
 * 
 * ArrayList is used in order to enable animations.
 *
 * @author Istarnion
 */
public class ImageLoader {
    private String resourceBasePath;
    
    private HashMap<String, ArrayList<BufferedImage>> imageMap;
    
    private GraphicsConfiguration gc;
    
    public ImageLoader(String resourceBasePath) {
        this.resourceBasePath = resourceBasePath;
        imageMap = new HashMap<String, ArrayList<BufferedImage>>();
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
    }
    
    public void loadImages(String instructionFile) {
        /*Format:
         * s <filename> = single image.
         * a <filename><number> = animated image (Animation strip) Number is the number is images in the strip.
         * m <filename><tilesize> = tilemap. The map is split into separate images. Tilesize is the size of the tiles in the map.
         */
        
        System.out.println(
                "Reading file "+instructionFile+", from "+resourceBasePath);
        
        String fnm = resourceBasePath+instructionFile;
        System.out.println(fnm+"...");
        InputStream in = this.getClass().getResourceAsStream(fnm);
        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(in))) {
            
            String line;
            char c;
            
            while((line = reader.readLine()) != null) {
                if(line.length() == 0) {
                    continue;
                }
                if(line.startsWith("//")) {
                    continue;
                }
                
                c = Character.toLowerCase(line.charAt(0));
                if(c == 's') {
                    loadSingleImage(line);
                }
                else if(c == 'a') {
                    loadStripImage(line);
                }
                else if(c == 'm') {
                	loadTilemap(line);
                }
                else {
                    System.out.println("Warning! The line '"+line+"' is invalid!");
                }
            }
        }
        catch(IOException e) {
            System.out.println("ERROR! Could not read "+instructionFile);
        }
        System.out.println("Done.");
    }
    
    private boolean loadSingleImage(String line) {
        String fnm = getFilename(line);
        String name = getName(fnm);
        
        if(imageMap.containsKey(name)) {
            System.out.println(name+" is already in use!");
            return false;
        }
        
        BufferedImage bi = loadImage(fnm);
        if(bi != null) {
            ArrayList<BufferedImage> a = new ArrayList<BufferedImage>();
            a.add(bi);
            imageMap.put(name, a);
            System.out.println("Stored "+fnm+", with key "+name);
            return true;
        }
        else {
            return false;
        }
    }
    
    private boolean loadStripImage(String line) {
        String fnm = getFilename(line);
        String name = getName(fnm);
        int number = -1;
        String s = getNumber(line);
        number = Integer.parseInt(s);
        
        if(imageMap.containsKey(name)) {
            System.out.println(name+" is already used!");
            return false;
        }
        if(number == -1) {
            return false;
        }
        
        loadStripImages(name, fnm, number);
        return true;
    }
    
    private boolean loadStripImages(String name,String filename, int number) {
        if(number < 0) {
            return false;
        }
        
        BufferedImage[] imageStrip = loadStripImageArray(filename, number);
        if(imageStrip == null) {
            return false;
        }
        
        ArrayList<BufferedImage> a = new ArrayList<BufferedImage>();
        int loadCount = 0;
        System.out.println(
                "Storing animated image "+filename+", with key "+name+".");
        System.out.print("Stored ");
        for(int i=0; i<imageStrip.length; i++) {
            loadCount++;
            a.add(imageStrip[i]);
            System.out.print(loadCount+", ");
        }
        
        if(a.size() <= 0) {
            System.out.println(" Failed!.");
            return false;
        }
        
        System.out.println(" Done.");
        imageMap.put(name, a);
        
        return true;
    }
    
    private boolean loadTilemap(String line) {
    	String fnm = getFilename(line);
        String name = getName(fnm);
        int number = -1;
        String s = getNumber(line);
        number = Integer.parseInt(s);
        
        if(imageMap.containsKey(name)) {
            System.out.println(name+" is already used!");
            return false;
        }
        if(number == -1) {
            return false;
        }
        
        loadTiles(name, fnm, number);
        return true;
    }
    
    private boolean loadTiles(String name, String filename, int tilesize) {
    	BufferedImage[][] tilemap = loadTilemapGrid(filename, tilesize);
    	ArrayList<BufferedImage> a;
    	
    	if(tilemap == null) {
    		return false;
    	}
    	
    	int loadCount = 0;
    	System.out.println("Storing tilemap: "+name+"...");
    	
    	for(int i=0; i<tilemap.length; i++) {
    		for(int j=0; j<tilemap[0].length; j++) {
    			System.out.println("Storing tile "+loadCount);
    			a = new ArrayList<BufferedImage>();
    			a.add(tilemap[j][i]);
    			
    			imageMap.put((loadCount+""), a);
    			loadCount++;
    		}
    	}
    	System.out.println("Done storing the tilemap");
    	return false;
    }
    
    /* --Accsess-methods-- */
    public boolean isLoaded(String name) {
        if(imageMap.containsKey(name)) {
            ArrayList<BufferedImage> a = (ArrayList<BufferedImage>)imageMap.get(name);
            if(a != null) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
    
    public boolean isAnimation(String name) {
    	if(imageMap.containsKey(name)) {
    		if(imageMap.get(name).size() > 1) {
    			return true;
    		}
    		return false;
    	}
    	return false;
    }
    
    public BufferedImage getImage(String name) {
        ArrayList<BufferedImage> a = (ArrayList<BufferedImage>)imageMap.get(name);
	if(a == null) {
	    System.out.println("No images stored under " + name);
	    return null;
	}
		return (BufferedImage)a.get(0);
    }
    
    public BufferedImage getImage(String name, int index) {
        ArrayList<BufferedImage> a = (ArrayList<BufferedImage>)imageMap.get(name);
	if(a == null) {
	    System.out.println("No images stored under " + name);
	    return null;
	}
        
        if(index < 0) {
            return (BufferedImage)a.get(0);
        }
        if(index >= a.size()) {
            return (BufferedImage)a.get(a.size()-1);
        }
	
	return (BufferedImage)a.get(index);
    }
    
    public ArrayList<BufferedImage> getImages(String name) {
        ArrayList<BufferedImage> a = (ArrayList<BufferedImage>)imageMap.get(name);
	if(a == null) {
	    System.out.println("No images stored under " + name);
	    return null;
	}
	
		return a;
    }
    
    public int getNumberOfImages(String name) {
        if(!imageMap.containsKey(name)) {
            return -1;
        }
        ArrayList<BufferedImage> a = (ArrayList<BufferedImage>)imageMap.get(name);
        return a.size();
    }
    
    public void addImage(String name, BufferedImage bi) {
        ArrayList<BufferedImage> a = new ArrayList<BufferedImage>();
        a.add(bi);
        imageMap.put(name, a);
    }
    /* --Access-methods end-- */
    
    /*Image input:*/
    private BufferedImage loadImage(String filename) {
        try {
            BufferedImage img = ImageIO.read(getClass().getResource(
                    resourceBasePath+filename));
            
            int transparency = img.getColorModel().getTransparency();
            BufferedImage copy = gc.createCompatibleImage(
                    img.getWidth(), img.getHeight(), transparency);
            
            Graphics2D g2d = copy.createGraphics();
            g2d.drawImage(img, 0, 0, null);
            g2d.dispose();
            
            return copy;
        }
        catch(IOException e) {
            System.out.println("ERROR! Could not read the file "+filename+". Does the file exist?");
            return null;
        }
    }
    
    /*String support-methods*/
    private String getFilename(String line) {
        StringTokenizer tokens = new StringTokenizer(line);
        tokens.nextToken();
        return tokens.nextToken();
    }
    
    private String getNumber(String line) {
        StringTokenizer tokens = new StringTokenizer(line);
        tokens.nextToken();
        tokens.nextToken();
        return tokens.nextToken();
    }
    
    private String getName(String filename) {
        int i;
        if((i = filename.lastIndexOf(".")) == -1) {
            System.out.println("No prefix found for "+filename);
            return filename;
        }
        else {
            return filename.substring(0, i);
        }
    }
    
    /*Method for splitting up the strip animation:*/
    private BufferedImage[] loadStripImageArray(String fnm, int number) {
        
        BufferedImage stripImg;
        if((stripImg = loadImage(fnm)) == null) {
            return null;
        }
        
        int width = stripImg.getWidth() / number;
        int height = stripImg.getHeight();
        int transparency = stripImg.getTransparency();
        
        BufferedImage[] stripArray = new BufferedImage[number];
        Graphics2D g2d;
        
        for(int i=0; i<number; i++) {
            stripArray[i] = gc.createCompatibleImage(width, height, transparency);
            g2d = stripArray[i].createGraphics();
            
            g2d.drawImage(stripImg, 0, 0, width, height,
                    (i*width), 0, (i*width)+width, height, null);
            g2d.dispose();
        }
        
        return stripArray;
    }
    
    private BufferedImage[][] loadTilemapGrid(String fnm, int tilesize) {
    	BufferedImage map; 
    	if((map = loadImage(fnm)) == null) {
    		return null;
    	}
    	
    	int transparency = map.getTransparency();
    	BufferedImage[][] tilemap = new BufferedImage[map.getWidth()/tilesize][map.getHeight()/tilesize];
    	Graphics2D g;
    	
    	for(int i=0; i<map.getWidth()/tilesize; i++) {
    		for(int j=0; j<map.getHeight()/tilesize; j++) {
    			tilemap[i][j] = gc.createCompatibleImage(tilesize, tilesize, transparency);
    			g = tilemap[i][j].createGraphics();
    			
    			g.drawImage(map, 0, 0, tilesize, tilesize, (i*tilesize), (j*tilesize),
    					(i*tilesize)+tilesize, (j*tilesize)+tilesize, null);
    			g.dispose();
    		}
    	}
    	return tilemap;
    }
}
