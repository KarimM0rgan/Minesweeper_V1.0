# 🧩 MineSweeper V1.0

# 🎮 About the Game
Minesweeper is a classic game where a grid has normal tiles and uncovered mines. The objective of the game is to uncover all tiles and flag all mines. If the player presses a mine, they lose. If they uncover all safe tiles, they win. More about the game [here](https://www.instructables.com/How-to-play-minesweeper/).


# 🚧 Implementation

The first version only contains one class, as it is a challenge for me to implement all the logic of the game and test that it's actually working before implementing the fancy, shiny stuff in the second version. 

The outline of the code starts with implementing the `main` method that creates an object of the type `MineSweeper` and runs the game using the `startGame` method.

Then, `start game` method si completed by giving out instructions, randomly spreading mines across the grid using the `setupField` method initiated after, and includes a while loop that ends the game when the player uncovers all safe tiles.

Afterwards, there is `buildHidden` method that creates a seperate grid to include the mines. Also, that method gives rise to neighboring tiles to display the number of mines in neighboring tiles.

Then, `playMove` comes to action where the player enters the numbers of row and column that they want to uncover their tile, and systematically, if the player presses a mine, it explodes and all other mines are revealed, and if they press a safe tile, it is revealed and the game proceeds. The consequences are shown on the grid through `fixVisible` and `fixNeighbors` methods where one is resposible to show tiles on the visible grid and the other is responsible to show the tile on the hidden one through `displayHidden` method.

There is a `MineSweeperTest.java` class where the implemented logic is tested.

# 🔮 Future Adjustments for V2.0

The GUI of the game will be implemented through java Swing graphics library. More classes, and thus test classes, will be implemented to handle subclasses. The flaging feature will be implemented, and a timer will be added. Finally, the user will be able to customize the grid however they want.

# 🏎️ Progress

I think the hardest part was implementing the logic and took me lots of research and youtube learning about floodfill method and java wireframe. I would love to believe that I have made it through 70% of the full game implementation. 