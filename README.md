# AI Routines
This is my first implementation of my LibGdx game's AI.

## How it works
The AI class(not included) executes the routines when in turn in a different thread; this prevents hanging on the render thread when the computations get a bit more complex in future versions.

## Classes
### Routine
An abstract class defining base Routine functions(think of it as a brain).

### RoutineFactory
Class containing static methods for Routine creation

### GetValidCards
An array of all valid cards is obtained by checking each card in hand using the CardValidator(not included) and passes the array to Play routine.

### Play
The AI uses a randomizer to pick the appropriate card to play

## Plans for the future
- Each card is associated with a real number that specifies its value(*importance*) based on card type. In future the AI should pick a combination of cards, as opposed to only a single card, and play a particular combination based on the combined value of the cards in the combination.
- Value of a combination should also be weighted according to difficulty chosen.
