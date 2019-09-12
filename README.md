Nottingham
This project contains 4 packages (goods, main, strategy, utilitary) encapsulating the code's main parts.

Goods:
	-It has 3 classes: Bonus, Goods and Royal Bonus.
	Bonus - is a class stores bonus values for illegal goods.
	Goods - is a enum class encapsulating goods characteristics.
	Royal Bonus - is a class encapsulating bonus values and logic
		for illegal goods.
Main:
	-It has 2 classes: GameInput, Main.
	GameInput - is a class specialized in reading input.
	Main - Central point of the project, containing games logic.
Strategy:
	-It has 6 classes, 1 enum class, 1 interface:
		AbstractPlayer, WizardPLayer, BasePlayer, BribePlayer, GreedyPlayer, PlayerComparator
		and Role and InterfacePlayer.
	InterfacePlayer - is a interface specifing minimum functionalities
	a player must have.
	AbstractPlayer - is a class implementing some common functionalities
	frequently reused or storing general data for every user.
	BasePlayer -  is a base player.
	WizardPlayer - is a wizard player.
	GreedyPlayer - is a greedy player.
	BribePlayer - is a bribed player.
	Role - is a enum class with players roles: sherrif and commerciant.
	PlayerComparator - implementing logic for Collections usage
	when working with players.
Utilitary:
	-It has 1 class: Pair
	Pair - is a class for pairing keys - values.
	
Program Flow:
	Main has the main logic, using players from package strategy
and cards/goods from goods package.
Uses a for loop to iterate through rounds. Each round does the following : replenishes players' hands,
makes their Bag, makes a inspection, puts goods on merchands' stand.
Finally calculates score, sorts players and prints podium.
All main methods discussed use specialized methods implemented
in strategy package's classes, to fit for every player's strategy.
