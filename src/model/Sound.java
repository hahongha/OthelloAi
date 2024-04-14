package model;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class Sound {
private HashMap< String , Clip> sound;
    public Sound() {
        sound = new HashMap<String, Clip>();
        openClip("ting.wav");
        openClip("piano.wav");
        openClip("victory.wav");
        openClip("lose.wav");
    }
    //mo file nhac
    private void openClip(String name){
        String path = "D:\\ha\\ki2nam3\\AI\\Othello\\Othello\\lib\\sounds\\"+ File.separator+ name;
        AudioInputStream inputStream = null;
        try {
            inputStream = AudioSystem.getAudioInputStream(new File(path));
            Clip clip = AudioSystem.getClip();
            clip.open(inputStream);
            sound.put(name, clip);
            inputStream.close();
        } catch (UnsupportedAudioFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //bat dau choi nhac
    public void play(String name, int loop) {
        if(sound.get(name).isRunning()) {
            sound.get(name).stop();
        }
        sound.get(name).start();
        sound.get(name).setFramePosition(0);//bat dau chay tu dau clip
        sound.get(name).loop(loop);
        System.out.println(name);
    }
    //dung nhac
    public void stop(String name) {
        if(sound.get(name).isRunning()) {
            sound.get(name).stop();
        }
    }
    //tiep tuc
    public void start(String name) {
        if(!sound.get(name).isRunning()) {
            sound.get(name).start();
        }
    }
    //bat tat nhac
    public void change() {
        if(sound.get("piano.wav").isRunning()||sound.get("lose.wav").isRunning()||sound.get("victory.wav").isRunning()) {
            stop("piano.wav");
            sound.get("ting.wav").close();
            sound.get("lose.wav").close();
            sound.get("victory.wav").close();
        }else {
            start("piano.wav");
            openClip("ting.wav");
            openClip("victory.wav");
            openClip("lose.wav");
        }
    }
    protected void changeVolume(String name, float value) {
        FloatControl fc = (FloatControl) sound.get(name).getControl(FloatControl.Type.MASTER_GAIN);
        fc.setValue(value);
    }
    protected double getVolume(String name) {
        FloatControl fc = (FloatControl) sound.get(name).getControl(FloatControl.Type.MASTER_GAIN);
        return fc.getValue();
    }
}