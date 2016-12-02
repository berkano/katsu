package ld37wu;

import katsu.KUI;

import java.util.HashMap;

/**
 * Created by shaun on 13/09/2015.
 */
public class LD37wuUI extends KUI {

    public HashMap<String, String> messageReplacements = new HashMap<String, String>();
    private String lastTextWritten = "";
    public HashMap<String, String> getMessageReplacements() {
        return messageReplacements;
    }

    @Override
    public void writeText(String s) {

        if (s.equals(lastTextWritten) && !s.contains("Paused")) return;
        lastTextWritten = s;

        super.writeText(doReplacements(s));

    }

    private String doReplacements(String originalText) {

        for (String r : messageReplacements.keySet()) {
            originalText = originalText.replace(r, messageReplacements.get(r));
        }

        return originalText;
    }

}
