A sample implementation is included (katsu.test.troll.TrollCastleGame) which can also be used for regression testing. Some behaviours to test are documented below.
***
* When I run the game, music starts playing.
***
* The title bar of the window shows "Troll Castle :: berkano :: Ludum Dare 38 - Small World".
***
* When the game starts, there is a splash screen displaying text.
* The splash screen has a translucent black backround and is properly centred in the window.
* The font has a distinctive style (i.e. not a default font).
* I can press H to close the splash screen.
***
* I can drag the map with the mouse.
***
* Trolls are rendered at different sizes and rotations.
* Grass and sea is rendered with jagged edges.
***
* I can click on a troll to select it.
* A white border appears around the selected troll.
* When a troll is selected, it is possible to select another troll instead.
* I can click on a troll and then on a mine and the troll will walk to the mine avoiding the other trolls.
* A sound effect plays when the troll walks.
* When the troll is over the mine I can press SPACE to make it go into the mine.
* A sound effect plays when the troll goes into the mine.
* The troll disappears while it is in the mine, and re-appears afterwards.
***
* I can click on a troll and then in the water, and the troll will walk to the edge of the water, but not go in (unless he already has a swim tube or is in psychedelic mode).
***
* A troll can plant a mushroom on a mud patch.
* A troll cannot eat a baby mushroom.
* The mushroom grows after a period of time.
* A troll can eat a fully grown mushroom.
* When a troll eats a fully grown mushroom it starts flashing different colours and walks a lot faster than usual.
* While the troll is flashing, some funky music plays.
* The troll can go into the sea while flashing.
* When the flashing finishes, the troll has a swim tube.
* When the flashing finishes, the normal music plays.
* The troll can go into the sea when it has a swim tube.
* When the troll comes out of the mine and moves away, the swim tube goes with it (if it dies in the mine, the swim tube is left outside - this is an unintentional "feature"!)
***
* There is a status bar at the top of the screen and it displays the number of trolls, amount of stone and gold, and progress on building the castle.
* The status bar displays multiple colours.
* There is a text console at the bottom of the screen. It has a dark translucent background and displays multiple colours.
* When I click on a troll, a greeting is shown in the text console.
* When I click on some grass, a description is shown in the text console.
* When I click on fish in the sea, a description is shown in the text console.
***
* When I press F11, the game becomes full screen, although it is stretched outside the original aspect ratio.
* When I press F11 for the second time, the game returns to a windowed mode.
***
* I can press H to display the help screen again during the game.
***
* When I press ESCAPE it exits the game.