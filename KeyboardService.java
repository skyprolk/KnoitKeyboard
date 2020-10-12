package com.skyproduction.knoit.keyboard;

import android.app.Service;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

public class KeyboardService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView keyboardView;
    private Keyboard keyboard;
    private boolean isCap = false;

    @Override
    public View onCreateInputView() {
        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_layout,null);
        keyboard = new Keyboard(this,R.xml.qwerty);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
        return keyboardView;

    }

    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int i, int[] ints) {

        InputConnection inputConnection = getCurrentInputConnection();
        playClick(i);
        switch (i){

            case Keyboard.KEYCODE_DELETE: inputConnection.deleteSurroundingText(1,0);
            break;

            case Keyboard.KEYCODE_SHIFT: isCap = !isCap;keyboard.setShifted(isCap);keyboardView.invalidateAllKeys();
            break;

            case Keyboard.KEYCODE_DONE: inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_ENTER));
            break;

            default:
                char code = (char)i;
                if(Character.isLetter(code)&& isCap)code = Character.toUpperCase(code);
                inputConnection.commitText(String.valueOf(code),1);
        }

    }

    private void playClick(int i) {
        AudioManager audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        switch (i){

            case 32:audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
            break;

            case Keyboard.KEYCODE_DONE: case 10:audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
            break;

            case Keyboard.KEYCODE_DELETE:audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
            break;
            default:audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);

        }

    }

    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
