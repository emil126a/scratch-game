package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.config.GameConfig;
import org.example.config.GameEvaluator;
import org.example.config.GameResult;
import org.example.config.MatrixGenerator;

import java.io.File;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {
            // Parse command-line arguments
         //   String configPath = null;
            double betAmount = 0;
            String configPath = "src/main/resources/config.json";
          /*  for (int i = 0; i < args.length; i++) {
                if (args[i].equals("--config")) configPath = args[++i];
             //   if (args[i].equals("--betting-amount")) betAmount = Double.parseDouble(args[++i]);
            }
            if (configPath == null || betAmount <= 0) {
                System.err.println("Usage: java -jar scratch-game.jar --config config.json --betting-amount <amount>");
                System.exit(1);
            }
*/
            // Load config
            ObjectMapper mapper = new ObjectMapper();
            GameConfig config = mapper.readValue(new File(configPath), GameConfig.class);

            // Generate matrix
            MatrixGenerator generator = new MatrixGenerator(config);
            String[][] matrix = generator.generateMatrix();

            // Evaluate game
            GameEvaluator evaluator = new GameEvaluator(config, betAmount);
            GameResult result = evaluator.evaluate(matrix);

            // Output result
            System.out.println(mapper.writeValueAsString(result));
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}