# 💵💰🪙🪎📜📑

# Budgethanteraren
### Elev: Jonatan Åström
## Sammanfattning
 Tyckte denna uppgiften till en början var svårare än den första vilket kanske inte är så konstigt egentligen då vi
 gått igenom mer invecklade begrepp och verktyg i Java. Intentionen var att denna gången börja med en bra plan så att 
 jag kunde koda lite strukturerat, vilket var en av lärdomarna jag tog med mig från förra uppgiften. Men det blev inte
 så. För att förstå uppgiften och hur jag skulle lösa den var enda sättet för mig att börja någonstans. Och sen när jag 
 väl hade satt igång tyckte jag att de nya verktygen i form av att jobba med streams och collections gjorde att det 
kändes mycket smidigare och effektivare än att fundera hur arrayer skulle fungera som i förra uppgiften. Att testa koden
var lite motigt måste jag erkänna och det blev att jag skrev många av testerna när mycket av koden redan var skriven. 
Men när man väl skrivit testerna och bara kunna testa många gånger med bara ett knapptryck
efter att man skrivit mer kod var ju så skönt.
 
Jag hade inte lika många problem med buggar i denna uppgiften som med förra men en bugg jag hittade var i min 
isValifBigDecimal(). Det var genom att skriva test till den koden där jag testade ett gränsfall. Jag trodde utfallet 
skulle bli true om jag skickade in 0. Men testet failade för den returnerade false. När jag sen testkörde programmet
märkte jag att mycket riktigt om jag matade in 0 så fastnade programmet på ett konstigt sätt. I det här fallet var det
ganska enkelt att se att det var ett logiskt fel i hur ja hade skrivit koden när jag gick in och kollade i
isValidBigDecimal(). Jag hade gjort så att den kastade exception om BigDecimal < 0 och true om BigDecimal > 0. Detta
betyder ju att fallet BigDecimal == 0 inte var intäkt som jag hade tänkt från början. Sen ändrade jag så att exception
kastades om BigDecimal <= 0. Eftersom det inte är någon nytta med att lägga in en transaktion som har värdet 0. 


