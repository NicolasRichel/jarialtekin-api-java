# Project Workflow

Actuellement le workflow du projet est très rudimentaire.  
Il existe 2 branches sur le dépôt :
 - `master` : branche principale qui doit toujours contenir la dernière version stable de l'appli.
 - `develop` : branche qui contient la version de l'appli en cours de développement (potentiellement instable).

Ainsi le workflow peut être résumé de la façon suivante :  
 **(1)** - Développer en pusher sur `develop`.  
 **(2)** - Créer une [Pull Request](https://github.com/NicolasRichel/jarialtekin-api-java/compare)
           (PR) '*from `develop` into `master`*'.  
 **(3)** - Merger la PR après relecture du code à intégrer dans `master`.  

**Note :** pour l'instant aucune procédure d'intégration ou de déploiement n'a été mis en
place car la version 1.0.0 de l'appli est encore en développement.
