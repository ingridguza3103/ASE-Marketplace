# ASE-Marketplace
ASE PROJECT

<h1>How to execute the project with docker</h1>
<ol>
    <li>In application.properties ensure that the right database configuration (under the comment "config for docker") is selected and the other commented out</li>
    <li>RUN mvn clean install</li> 
    <li>RUN IN TERMINAL: docker-compose up --build </li>
</ol>

How to connect to the database:
1. EXECUTE IN TERMINAL: docker exec -it marketplace-postgres-1 psql -U myuser -d mydatabase 
2. EXECUTE: \c mydatabase
3. EXECUTE: \d to see the relations
4: QUERYING: e. g. SELECT * FROM users; (note ; at the end is important)

<h1>How to execute the project locally</h1>
<ol>
    <li>In application.properties ensure that the right database configuration (under the comment "configuration for local run") is selected and the other commented out
    Furthermore ensure that the database credentials equal the ones of your local postgresql configuration (it should be username: postgres and password: postgres by default).</li>
    <li>Adjust the database credentials in the compose.yml for the postgres service to match the configuration in application.properties</li>
    <li>Start the postgres service</li>
    <li>If postgres service is up and running execute the MarketPlaceApplication.</li>
</ol>

How to connect to the database:
1. EXECUTE IN TERMINAL: psql -h localhost -U postgres -d mydatabase
4. EXECUTE: \d to see the relations
4: QUERYING: e. g. SELECT * FROM users; (note ; at the end is important)

<h2>Important Note i will try to automate the switch between local execution and running it in docker</h2>

