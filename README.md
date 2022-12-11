## Del 1 - DevOps-prinsipper

Utfordringer med dagens løsning er mange. Det er for det første ingen garanti for at koden bygges, siden CI ikke er introdusert riktig. \
Det er også manglende testdekning og foreløpig kjører ikke unit-testene. Deployment er også veldig komplisert og lite automatisert.
Problemstillingen med mindre hyppige releaser er at kunder får ikke software de ønsker. Også er "wall-of-confusion" fenomenet veldig tilstede 
dersom det ikke er samstemt mellom utviklere og QA. I slike tilfeller vil det ofte gå mye tid og undøvendig ressursbruk for å samkjøre mellom QA/testere og utviklere. 

Et bedre scenario er å ha et godt sett med unit-tester, integrasjons-tester, evt API-tester for koden. Disse skal da 
kjøres som automatiserte tester som kjøres hver gang koden blir publisert til versjonskontroll. Det er mange gevinster å gå for denne tilnærmingen,
som det nevnt over men også faktumet at utviklerne kan enklere gjøre endringer i koden/pipelines som fører til at driften går som den skal. Man holder da 
problem som oppstår på "samme sted". Forenkling med Docker og releases er også veldig nyttig. Om man lager et Docker image som vi lagrer i et container registry 
vil det være veldig enkelt å rulle frem og tilbake i versjons-historikken. Dersom noe knekker i produksjon etter en release, ruller man tilbake
til forrige versjon i mens man jobber på en bugfix. Hyppige leveranser gjør at man finner bugs tidligere, størrelsen på leveransen er mindre, som igjen gjør 
sannsynligheten for at noe går galt mindre. I tillegg har alltid kunden nyeste features tilgjengelig.

## Del 2 - CI

#### Oppgave 3:
* For å nekte mulighet til å pushe direkte på main branch kan man gå inn i settings => branches og legge til en branch protection rule.\
Her kan du skrive inn main og checke "Require a pull request before merging".
* For å i tillegg kreve godkjent review før mulighet til å merge sørger du for at "Require approvals" er checked med 1 som required approval. Det siste er default.
* Du kan checke "Require status checks to pass before merging" for å sørge for at feature branchen PRen er basert på er verifisert av Github Actions.

## Del 4 - Docker
#### Oppgave 1:
For å få workflowen til å fungere med min DockerHub konto må jeg legge inn mitt brukernavn og en access token som secrets i GitHub. I utganspunktet feiler workflowen fordi den ikke klarer å autentisere mot DockerHub.

#### Oppgave 3:
For å kunne laste opp container image til eget ECR repo må sensor legge inn noen secrets i GitHub. Legg inn AWS_ACCESS_KEY_ID og AWS_SECRET_ACCESS_KEY. I tillegg endre env variablene `ECR_REGISTRY`, `ECR_REPOSITORY` og `AWS_REGION` under `Build, tag, and push image to Amazon ECR` jobben i `docker.yml`.