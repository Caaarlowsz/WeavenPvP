package br.com.skyprogrammer.cophenix.zenixpvp.utilitaries.strings;

import java.util.ArrayList;
import java.util.List;

public class StringAnimated
{
    private String stringToAnimate;
    private List<String> listOfString;
    private int sizeOfTheAnimation;
    private boolean localBoolean;
    
    public StringAnimated(final String stringToAnimate, final String stringColor1, final String stringColor2, final String stringColor3, final int sizeOfTheAnimation) {
        this.stringToAnimate = stringToAnimate;
        this.listOfString = new ArrayList<String>();
        this.createFrames(stringColor1, stringColor2, stringColor3, sizeOfTheAnimation);
    }
    
    private void createFrames(final String localString1, final String localString2, final String localString3, final int sizeInteger) {
        if (this.stringToAnimate != null && !this.stringToAnimate.isEmpty()) {
            for (int i = 0; i < this.stringToAnimate.length(); ++i) {
                if (this.stringToAnimate.charAt(i) != ' ') {
                    this.listOfString.add(String.valueOf(localString1) + this.stringToAnimate.substring(0, i) + localString2 + this.stringToAnimate.charAt(i) + localString3 + this.stringToAnimate.substring(i + 1));
                }
            }
            for (int i = 0; i < sizeInteger; ++i) {
                this.listOfString.add(String.valueOf(localString1) + this.stringToAnimate);
            }
            for (int i = 0; i < this.stringToAnimate.length(); ++i) {
                if (this.stringToAnimate.charAt(i) != ' ') {
                    this.listOfString.add(String.valueOf(localString3) + this.stringToAnimate.substring(0, i) + localString2 + this.stringToAnimate.charAt(i) + localString1 + this.stringToAnimate.substring(i + 1));
                }
            }
            for (int i = 0; i < sizeInteger; ++i) {
                this.listOfString.add(String.valueOf(localString3) + this.stringToAnimate);
            }
        }
    }
    
    public String next() {
        if (this.listOfString.isEmpty()) {
            return "";
        }
        if (this.localBoolean) {
            --this.sizeOfTheAnimation;
            if (this.sizeOfTheAnimation <= 0) {
                this.localBoolean = false;
            }
        }
        else {
            ++this.sizeOfTheAnimation;
            if (this.sizeOfTheAnimation >= this.listOfString.size()) {
                this.localBoolean = true;
                return this.next();
            }
        }
        return this.listOfString.get(this.sizeOfTheAnimation);
    }
}
