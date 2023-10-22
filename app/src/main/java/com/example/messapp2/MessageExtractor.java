package com.example.messapp2;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageExtractor {

    public static String[] extractMessages(String html) {
        // Define the regular expression to extract each message from the <p> tags
        String regex = "<p>User ID: \\d+, Message: (.*?), Timestamp: \\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}</p>";

        // Create a pattern object with the regular expression
        Pattern pattern = Pattern.compile(regex);

        // Create a matcher object with the HTML string
        Matcher matcher = pattern.matcher(html);

        // Create an ArrayList to store the extracted messages
        List<String> messagesList = new ArrayList<>();

        // Find all occurrences of the regex pattern and add each match to the list
        while (matcher.find()) {
            String message = matcher.group(1);
            messagesList.add(message);
        }

        // Convert the ArrayList to an array of strings
        String[] messagesArray = messagesList.toArray(new String[0]);

        return messagesArray;
    }



    public static List<String> extractUserIds(String html) {
        // Define the regular expression to extract each user ID from the <p> tags
        String regex = "<p>User ID: (\\d+), Message: .*?, Timestamp: \\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}</p>";

        // Create a pattern object with the regular expression
        Pattern pattern = Pattern.compile(regex);

        // Create a matcher object with the HTML string
        Matcher matcher = pattern.matcher(html);

        // Create a list to store the extracted user IDs
        List<String> userIds = new ArrayList<>();

        // Find all occurrences of the regex pattern and add each match to the list
        while (matcher.find()) {
            String userId = matcher.group(1);
            userIds.add(userId);
        }

        return userIds;
    }

    public static List<String> extractTime(String html) {
        // Define the regular expression to extract each user ID from the <p> tags
        String regex = "<p>User ID: \\d+, Message: .*?, Timestamp: \\d{4}-\\d{2}-\\d{2} (\\d{2}:\\d{2}):\\d{2}</p>";

        // Create a pattern object with the regular expression
        Pattern pattern = Pattern.compile(regex);

        // Create a matcher object with the HTML string
        Matcher matcher = pattern.matcher(html);

        // Create a list to store the extracted user IDs
        List<String> time = new ArrayList<>();

        // Find all occurrences of the regex pattern and add each match to the list
        while (matcher.find()) {
            String userId = matcher.group(1);
            time.add(userId);
        }

        return time;
    }

}