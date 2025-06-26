## Building the Project

1. **Navigate to the Project Directory**:
   ```bash
   cd scratch-game/scratch-game
   ```

2. **Build the Project**:
   Use Maven to clean and package the project, creating a fat JAR that includes all dependencies:
   ```bash
   mvn clean package
   ```
    - `clean`: Removes previous build artifacts from the `target` directory.
    - `package`: Compiles the code, runs tests, and creates `target/scratch-game-1.0-SNAPSHOT.jar`.

   The resulting JAR is located at `target/scratch-game-1.0-SNAPSHOT.jar`.

## Running the Application

Run the game with the following command, specifying the path to the configuration file and the betting amount:

```bash
java -jar target/scratch-game-1.0-SNAPSHOT.jar --config src/main/resources/config.json --betting-amount 100
```

- **Parameters**:
    - `--config <path>`: Path to the `config.json` file (e.g., `src/main/resources/config.json`).
    - `--betting-amount <amount>`: The betting amount (e.g., `100`).

- **Output**:
  The application outputs a JSON object to the console, containing:
    - `matrix`: The generated 2D matrix (e.g., 3x3 grid of symbols).
    - `reward`: The calculated reward based on winning combinations and bonuses.
    - `applied_winning_combinations`: A map of symbols to their applied win conditions.
    - `applied_bonus_symbol`: The applied bonus symbol (or null if none or "MISS").

  Example output:
  ```json
  {
      "matrix": [
          ["A", "B", "B"],
          ["A", "+1000", "B"],
          ["A", "B", "B"]
      ],
      "reward": 1600,
      "applied_winning_combinations": {
          "A": ["same_symbol_3_times", "same_symbols_vertically"],
          "B": ["same_symbol_3_times"]
      },
      "applied_bonus_symbol": "+1000"
  }
  ```