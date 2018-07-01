# Installation de JarialTekin API en local

### Pré-requis

 - Java 8
 - Maven 3.x
 - MariaDB 10.3


### Initialisation de la BDD

Avant de pouvoir démarrer l'application sur votre poste de développement vous
devez disposer d'une base de données correctement initialisée.  
JarialTekin API est initialement développée pour fonctionner avec une base de
données MariaDB 10.3 (une version antérieure risque de ne pas convenir...).
Le dossier `database/` contient un ensemble de scripts SQL qui permettent
notamment de créer la base de données `jarialtekin_db` (avec toutes les tables,
vues et/ou procédures nécessaires) et de peupler cette dernière avec des données
de test pour pouvoir développer localement :

 - fichier `create_database.sql` : création de la base de données.
 - fichier `populate_db.sql` : insertion de données de test.

Le fichier `src/main/resources/db.properties` permet de configurer la connexion à
la base de données (voir aussi : *fr.devnr.jariatekinapi.dao.factory.DataSourceFactory*).

**Remarque :** les tests inclus également un test de la base de données pour verifier 
que le schéma (en particulier les contraintes d'intégrité) est conforme. Ces tests
sont implémentés dans la classe *fr.devnr.jarialtekinapi.db.DBTest*, cependant ils
sont désactivés par défaut lors d'un build avec Maven (`mvn clean package`).
Pour activer ces tests durant le build il faut modifier le `pom.xml` pour que les
tests avec le tag 'db_tests' soit inclus :
```
<build>
  <plugins>
    ...
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-surefire-plugin</artifactId>
      <version>${mvnSurefirePluginVersion}</version>
      <configuration>
        ...
        <properties>
          <includeTags>db_tests</includeTags>
          <!-- <excludeTags>db_tests</excludeTags> -->
        </properties>
        ...
      </configuration>
      ...
    </plugin>
    ...
  </plugins>
  ...
</build>
```


### Installation et démarrage de l'appli en local

**(1)** : Récupérer les sources du projet :
```
$ git clone https://github.com/NicolasRichel/jarialtekin-api-java.git
$ cd jarialtekin-api-java/
```

**(2)** : Compiler le projet :
```
$ mvn clean package
```

**(3)** : Démarrer l'appli en local :
```
$ mvn -Pdev jetty:run
```

La web app est alors accessible en local : http://localhost:8080
