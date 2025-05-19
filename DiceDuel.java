// DiceDuel.java

import java.util.Random;
import java.util.Scanner;

public class DiceDuel {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    public static void main(String[] args) {
        printWelcome();
        AIOpponent ai = chooseOpponent();
        printIntro(ai);
        gameLoop(ai);
    }

    // prints title
    private static void printWelcome() {
        System.out.println("\n=== aesthetic dice duel ===\n");
    }

    // prints ai ascii and intro
    private static void printIntro(AIOpponent ai) {
        System.out.println(Characters.getAscii(ai.name));
        System.out.println("\n" + ai.name + ": " + ai.introLine);
        System.out.println("---");
    }

    // main game loop
    private static void gameLoop(AIOpponent ai) {
        int playerScore = 0;
        int aiScore = 0;

        while (playerScore < 3 && aiScore < 3) {
            System.out.print("\nchoose your dice (D4, D6, D8, D10, D12, D20) or type 'exit': ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("EXIT")) {
                System.out.println("\nexiting.");
                return;
            }

            int sides = parseDice(input);
            if (sides <= 0) {
                System.out.println("invalid input.");
                continue;
            }

            rollAnimation();

            int playerRoll = rollDice(sides);
            int aiRoll = rollDice(sides);

            System.out.println("\nyou rolled: " + playerRoll);
            System.out.println("ai rolled:  " + aiRoll);
            System.out.println("\n" + ai.name + " says: " + ai.getRandomTaunt());

            if (playerRoll > aiRoll) {
                playerScore++;
                System.out.println("you win this round.");
                System.out.println(ai.name + ": " + ai.loseLine);
            } else if (aiRoll > playerRoll) {
                aiScore++;
                System.out.println("ai wins this round.");
                System.out.println(ai.name + ": " + ai.winLine);
            } else {
                System.out.println("tie.");
                System.out.println(ai.name + ": " + ai.tieLine);
            }

            System.out.println("\nscore — you: " + playerScore + "  ai: " + aiScore);
        }

        System.out.println("\nfinal result:");
        if (playerScore > aiScore) {
            System.out.println("you win the game.");
            System.out.println(ai.name + ": " + ai.loseLine);
        } else {
            System.out.println("ai wins the game.");
            System.out.println(ai.name + ": " + ai.winLine);
        }
    }

    // parses dice input
    private static int parseDice(String input) {
        if (input.matches("D\\d+")) {
            return Integer.parseInt(input.substring(1));
        }
        return -1;
    }

    // rolls a dice with given sides
    private static int rollDice(int sides) {
        return random.nextInt(sides) + 1;
    }

    // simulates rolling animation
    private static void rollAnimation() {
        System.out.print("rolling");
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(300);
                System.out.print(".");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }

    // lets player choose ai opponent
    private static AIOpponent chooseOpponent() {
        AIOpponent[] all = Characters.ALL;
        System.out.println("choose your ai opponent:\n");
        for (int i = 0; i < all.length; i++) {
            System.out.println((i + 1) + ". " + all[i].name + " — " + all[i].bio);
        }

        while (true) {
            System.out.print("\nenter number (1–" + all.length + "): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= 1 && choice <= all.length) {
                    return all[choice - 1];
                }
            } catch (Exception e) {
                // do nothing
            }
            System.out.println("invalid input.");
        }
    }
}

class AIOpponent {
    public final String name;
    public final String bio;
    public final String introLine;
    public final String winLine;
    public final String loseLine;
    public final String tieLine;
    public final String[] taunts;

    public AIOpponent(String name, String bio, String introLine, String winLine, String loseLine, String tieLine, String[] taunts) {
        this.name = name;
        this.bio = bio;
        this.introLine = introLine;
        this.winLine = winLine;
        this.loseLine = loseLine;
        this.tieLine = tieLine;
        this.taunts = taunts;
    }

    public String getRandomTaunt() {
        return taunts[(int)(Math.random() * taunts.length)];
    }
}

class Characters {
    public static final AIOpponent[] ALL = {
            new AIOpponent("Zero", "a stoic synthetic mind forged in a probability lab. says little, wins often.", "you will understand inevitability.", "the expected outcome.", "unacceptable.", "...", new String[]{"...", "your odds are... declining.", "resistance is illogical."}),
            new AIOpponent("Luna", "a wandering AI who consults stars, dice, and dreams. graceful and eerie.", "the stars whisper... your defeat.", "i am aligned with the moon’s favor.", "you’re radiant today.", "chance is soft tonight.", new String[]{"just a glimmer...", "shine while you can.", "fate is a dancer."}),
            new AIOpponent("Byte", "a corrupted debug bot obsessed with entropy. glitchy and chaotic.", "*static* i will consume this sequence.", "error: you failed.", "nooo... buffer underrun...", "sync lost. rerolling...", new String[]{"1s and 0s. you’re a 0.", "011010... your roll was trash.", "dice.exe: crushed."}),
            new AIOpponent("Kuro", "a shadow-born entity who believes in pure chance as a form of justice.", "your path ends here.", "justice has been served.", "the void whispers of your luck.", "balance is maintained.", new String[]{"feel the weight of chance.", "cold. calculated. flawless.", "watch and despair."}),
            new AIOpponent("Dot", "a cheerful experimental companion bot, always optimistic and kind.", "hey there! ready to roll?", "woohoo! that was close!", "wow, nice roll!", "same again? okay!", new String[]{"you’re doing great!", "high five!", "this is fun!"}),
            new AIOpponent("Echo", "a sarcastic mirror-AI that mocks and mimics human behavior. smug.", "oh, you picked me? risky.", "called it. again.", "beginner’s luck... right?", "even dice get bored.", new String[]{"yawn.", "is that all?", "i'm rolling circles around you."}),
            new AIOpponent("Nova", "an overconfident AI champion obsessed with perfection and flair.", "brace yourself for brilliance.", "another flawless strike.", "impossible. no—unacceptable.", "how... quaint.", new String[]{"prepare to be dazzled.", "effortless.", "i make this look easy."}),
            new AIOpponent("Flux", "a manic chaos entity who believes in pure randomness and vibes.", "roll fast, think never!", "YESSSS! that was wild!", "whoops! i’ll get you next time!", "ugh, boring tie.", new String[]{"spin the wheel!", "chaos loves me more!", "unpredictable. unrepeatable."}),
            new AIOpponent("Cass", "a poetic AI that romanticizes chance and fate. often quotes itself.", "chance bends where fate wills.", "the dice wrote your downfall.", "your luck... is lyrical.", "a shared stanza in the poem.", new String[]{"you hear the echo of loss?", "even odds betray the hopeful.", "beauty lies in the unexpected."}),
            new AIOpponent("Ion", "an efficient logic-processor AI that calculates your every move.", "probabilities aligned. let’s begin.", "expected result: victory.", "calculations... flawed.", "neutral result. try again.", new String[]{"i predicted this.", "you are within error margin.", "luck is a model."})
    };

    public static String getAscii(String name) {
        return switch (name) {
            case "Zero" -> "[ZERO]\n   ▄▄▄▄▄\n  █     █\n █  ▄▄▄  █\n █ █   █ █\n █ █▄▄▄█ █\n █       █\n  █▄▄▄▄▄█";
            case "Luna" -> "[LUNA]\n     ★\n    /|\\\n   /_|_\\\n    / \\\n   *   *";
            case "Byte" -> "[BYTE]\n   ▓▓▓▓▓\n  ▓░░░░▓\n ▓░ ░ ░▓\n ▓░▓▓▓░▓\n ▓░░░░░▓\n  ▓▓▓▓▓";
            case "Kuro" -> "[KURO]\n    ███\n   █   █\n   █ █ █\n   ████ \\n   █  █ █\n    ██";
            case "Dot" -> "[DOT]\n    o o\n   ( ^ )\n    \\_/";
            case "Echo" -> "[ECHO]\n    ╭─╮\n    │o│\n    ╰─╯\n   /   \\\n  (_____)";
            case "Nova" -> "[NOVA]\n   ✧╮╭✧\n  ╭╯╰╯╰╮\n  ╰╮╭╮╭╯\n   ╰╯╰╯";
            case "Flux" -> "[FLUX]\n   ∆∆∆∆∆\n  ∆     ∆\n ∆  o o  ∆\n ∆   ^   ∆\n  ∆_____∆";
            case "Cass" -> "[CASS]\n   /\\_/\\\n  ( o o )\n  ==_Y_==\n   /   \\\n  (_____)";
            case "Ion" -> "[ION]\n   ░░░░░\n  ░░   ░░\n ░░ ░ ░ ░░\n ░░  ░  ░░\n  ░░░░░░░";
            default -> "[???]";
        };
    }
}
