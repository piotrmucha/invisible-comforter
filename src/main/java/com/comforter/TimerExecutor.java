package com.comforter;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class TimerExecutor extends TimerTask {
    private KeyListener keyListener;
    private MyFrame window;
    Map<String, Double> values = new HashMap<>();

    public TimerExecutor(KeyListener keyListener, MyFrame frame) {
        this.keyListener = keyListener;
        this.window = frame;
        try {
            readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readFile() throws IOException {
        BufferedReader br = null;
        InputStream inputStream = getClass()
                .getClassLoader().getResourceAsStream("Andbrain_DataSet.csv");
        br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line = "";
        br.readLine();
        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            // use comma as separator
            String[] cols = line.split(",");
            values.put(cols[0].trim(), Double.parseDouble(cols[5]));
        }
        Map<String, Double> sortedMap =
                values.entrySet().stream()
                        .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));
    }

    private void triggerDisplayMem() {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                ArrayList<String> givenList = window.getUrls();
                Random rand = new Random();
                String randomUrl = givenList.get(rand.nextInt(givenList.size()));
                Desktop.getDesktop().browse(new URI(randomUrl));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastChange = keyListener.getLastTimeWhenUserUseKeyboard();
        String wordsToAnalyze = keyListener.getCharactersFromUser().toString();
        wordsToAnalyze = wordsToAnalyze.trim();
        wordsToAnalyze = wordsToAnalyze.replaceAll("[^A-Za-z0-9 ]", "");
        if (now.isAfter(lastChange.plusSeconds(12)) && wordsToAnalyze.length() > 5) {
            try {
                System.out.println("Sentence analyzing now: " + wordsToAnalyze);
                String[] singleWords = wordsToAnalyze.split(" ");
                List<Double> listOfProbabilites = new ArrayList<>();
                for (String word : singleWords) {
                    Double probability = values.get(word);
                    if (probability != null) {
                        listOfProbabilites.add(probability);
                    }
                }
                int amount = 0;
                if (!listOfProbabilites.isEmpty()) {
                    for (Double probab : listOfProbabilites) {
                        if (probab > 0.025) {
                            amount++;
                        }
                    }
                }
                if (amount >= 2) {
                    triggerDisplayMem();
                    System.out.println("You are sad!");
                } else {
                    System.out.println("You are not sad!");
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            keyListener.clearCharacters();
        }

    }
}
