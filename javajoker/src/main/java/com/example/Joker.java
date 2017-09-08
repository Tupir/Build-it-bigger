package com.example;

import java.util.Random;

public class Joker {

    private static final Random random = new Random();

    public static final String[] JOKES = {
            "Microsoft gives you Windows, Linux gives you a home!",
            "What did one ocean say to the other ocean? Nothing, they just waved.",
            "Can a kangaroo jump higher than a house? Of course, a house does not jump at all.",
            "I bought some shoes from a drug dealer. I don't know what he laced them with, but I've been tripping all day.",
            "I told my girlfriend she drew her eyebrows too high. She seemed surprised.",
            "Two clowns are eating a cannibal. One turns to the other and says \"I think we got this joke wrong\"",
            "My wife told me I had to stop acting like a flamingo. So I had to put my foot down.",
            "My dog used to chase people on a bike a lot. It got so bad, finally I had to take his bike away.",
    };

    public String[] getJokes() {
        return JOKES;
    }


    public String getRandomJoke() {
        return JOKES[random.nextInt(JOKES.length)];
    }

}
