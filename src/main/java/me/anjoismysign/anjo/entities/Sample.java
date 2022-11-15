package me.anjoismysign.anjo.entities;

import me.anjoismysign.anjo.swing.AnjoComponent;
import me.anjoismysign.anjo.swing.AnjoPane;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anjoismysign
 */
public class Sample {
    private String minecraftUsername, discordUsername;

    /**
     * @param minecraftUsername the minecraft username
     * @param discordUsername   the discord username
     */
    public Sample(String minecraftUsername, String discordUsername) {
        this.minecraftUsername = minecraftUsername;
        this.discordUsername = discordUsername;
    }

    /**
     * builds a Sample object which makes use of AnjoPane &amp; AnjoComponent
     *
     * @return the Sample object
     */
    public static Sample build() {
        AnjoComponent component1 = new AnjoComponent("Minecraft username", new JTextField());
        AnjoComponent component2 = new AnjoComponent("Discord username:", new JTextField());
        AnjoComponent component3 = new AnjoComponent("Age:", new JTextField());
        List<AnjoComponent> componentes = new ArrayList<>();
        componentes.add(component1);
        componentes.add(component2);
        AnjoPane anjoPane = AnjoPane.build(componentes, "Anjo's SMP Form", 2, null);
        String minecraftUsername = anjoPane.getTextFieldText(0);
        String discordUsername = anjoPane.getTextFieldText(1);
        ShortResult result = anjoPane.getShort(2);
        if (!result.isValid()) {
            build();
            return null;
        }
        short age = result.value();
        if (age > 13) {
            JOptionPane.showMessageDialog(null, "Remember to keep it PG-13!");
            return null;
        }
        return new Sample(minecraftUsername, discordUsername);
    }

    /**
     * @return the minecraft username
     */
    public String getMinecraftUsername() {
        return minecraftUsername;
    }

    /**
     * sets the minecraft username
     *
     * @param minecraftUsername the username
     */
    public void setMinecraftUsername(String minecraftUsername) {
        this.minecraftUsername = minecraftUsername;
    }

    /**
     * @return the discord username
     */
    public String getDiscordUsername() {
        return discordUsername;
    }

    /**
     * sets the discord username
     *
     * @param discordUsername the username
     */
    public void setDiscordUsername(String discordUsername) {
        this.discordUsername = discordUsername;
    }
}
