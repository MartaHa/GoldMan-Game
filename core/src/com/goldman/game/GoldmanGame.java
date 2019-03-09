package com.goldman.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

public class GoldmanGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture background;
    Texture[] man;
    int manState = 0;
    int pause = 0;
    float gravity = 0.2f;
    float velocity = 0;
    int manY = 0; // wysokosc ludzika
    Random random;
    ArrayList<Integer> coinX = new ArrayList<Integer>();
    ArrayList<Integer> coinY = new ArrayList<Integer>();
    Texture coin;
    int coinCount;
    ArrayList<Integer> bombX = new ArrayList<Integer>();
    ArrayList<Integer> bombY = new ArrayList<Integer>();
    Texture bomb;
    int bombCount;


    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("bg.png");
        man = new Texture[4];
        man[0] = new Texture("frame-1.png");
        man[1] = new Texture("frame-2.png");
        man[2] = new Texture("frame-3.png");
        man[3] = new Texture("frame-4.png");
        manY = Gdx.graphics.getHeight() / 2;
        coin = new Texture("coin.png");
        bomb = new Texture("bomb.png");
        random = new Random();
    }

    public void generateCoin(){
        float height = random.nextFloat()* Gdx.graphics.getHeight();
        coinY.add((int) height);
        coinX.add(Gdx.graphics.getWidth());
    }
    public void generateBomb(){
        float height = random.nextFloat()* Gdx.graphics.getHeight();
        bombY.add((int) height);
        bombX.add(Gdx.graphics.getWidth());
    }


    //batch wykonuje sie cały czas
    @Override
    public void render() {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (coinCount<100){
            coinCount++;
        }else{
            coinCount=0;
            generateCoin();
        }
        for (int i=0; i< coinY.size(); i++){
            batch.draw(coin, coinX.get(i), coinY.get(i));
            coinX.set(i, coinX.get(i)-4);
        }

        if (bombCount<300){
            bombCount++;
        }else{
            bombCount=0;
            generateBomb();
        }
        for (int i=0; i< bombY.size(); i++){
            batch.draw(bomb, bombX.get(i), bombY.get(i));
            bombX.set(i, bombX.get(i)-4);
        }


        //przy dotknieciu
        if(Gdx.input.justTouched()){
            velocity=-10;
        }

        if (pause < 8) {
            pause++;
        } else {
            pause = 0;
            if (manState < 3) {
                manState++;
            } else {
                manState = 0;
            }
        }
        velocity += gravity;
        manY -= velocity;

        if(manY <=0)
            manY=0;

        batch.draw(man[manState], Gdx.graphics.getWidth() / 2 - man[manState].getWidth() / 2, manY);
        batch.end();
    }


    @Override
    public void dispose() {
        batch.dispose();

    }
}
