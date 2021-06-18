# Zadanie A3 - Sprawy urzędowe
Celem zadania jest skonstruowanie aplikacji klient-serwer służącej do zlecania spraw urzędowych i uzyskiwania
informacji o ich wyniku. Czas rozpatrzenia sprawy przez urząd jest zgrubnie określany już w momencie jej zgłaszania
i może wynosić około godziny-dwóch (na potrzeby realizacji i demonstracji zadania należy oczywiście przyjąć, że 
jest krótszy). Zlecający (klient) jest zainteresowany jak najszybszym dowiedzeniem się o wyniku zgłoszonej przez
siebie sprawy, jednak spóźnienie rzędu minuty czy dwóch nie będzie stanowić problemu. Nie można założyć, że każdy
klient będzie chciał mieć uruchomioną aplikację przez cały czas realizacji sprawy.  

Urząd obsługuje wiele różnych typów spraw (różniących się zestawem przesyłanych lub zwracanych informacji), 
można przyjąć, że wszystkie typy spraw są znane w czasie tworzenia systemu. (na potrzeby realizacji zadania 
wystarczy przewidzieć trzy przykładowe).  

Priorytetem w realizacji zadania jest dobór, zaprojektowanie i realizacja właściwego sposobu komunikacji – 
minimalizującego liczbę niepotrzebnych wywołań. Dopuszczalne są wszystkie eleganckie opcje realizacji komunikacji.  

<u>Technologia middleware</u>: dowolna  
<u>Demonstracja zadania</u>: aplikacja kliencka musi pozwalać na efektywne przetestowanie różnych scenariuszy  
<u>Języki programowania</u>: dwa (jeden dla klienta, drugi dla serwera)  
<u>Maksymalna punktacja</u>: 7  