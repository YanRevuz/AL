# Explication du P.O.C

**ATTENTION** : Ce code permet de réaliser uniquement le schéma ci-dessous. Il ne peut en aucun cas de réaliser d’autres applications si on y rajoute des composants et des services.

Tout le code se trouve dans le package “MyExemple”. 

Le but de cet exemple est de réaliser une connexion entre deux composants.
 
Dans le Main, 3 composants sont créés avec un service chacun et 3 services qui demandent des interfaces.

![Schéma del'application réalisée par le POC](https://github.com/YanRevuz/AL/blob/master/Asset/shema.png)

Voici l’application obtenue à la fin de la simulation.

Comme nous n’avons pas d’interface, le choix de l’utilisateur sur le premier composant est réalisé dans le Main

![](https://github.com/YanRevuz/AL/blob/master/Asset/code.png)

# Etat des agents

Chaque agent va alterner infiniment entre deux états :
- L’état perception : dans cet état chaque agent va percevoir son environnement.
- L’état décision : dans cet état chaque agent va effectuer une action en fonction de ce qu’il a perçu.

# Output lors d’une exécution du code :

- J'ai clique sur c1 en tant que user **//Choix de l'utilisateur**
- C1 dit qu'il a reçu une demande de connexion. Il va donc la propager à son service
- S1 dit : Je vais broadcast et demander l'interface I1
- S2 dit qu'il fournit bien l'interface I1 et donc qu'il propage la demande à son composant
- S3 dit qu'il ne fournit pas l'interface I1 et donc qu'il propage pas la demande à son composant
- C2 dit qu'il peut etre connecté et repond positivement à S2
- S2 dit que son composant peut etre connecté donc il repond positivement
- S1 dit qu'il se connecte à S2 et il dit     à son composant qu'il est connecté
- Une application a été trouvée

