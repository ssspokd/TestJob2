# TestJob2
Задание
Необходимо разработать консольное Java (версии 5-7 – на выбор) приложение рассчитывающее начисление абоненту за услуги. Сервис позволяет определять услуги с различными операциями. В данной версии должно быть предусмотрено 3 операции: multiply – умножение введенного значения на коэффициент, constant – всегда возвращает определенное  значение (в случае попытки добавить значение для расчета выдает на экран предупреждение), average – считает среднее по введенным значениям. Для всех операций в случае недостаточных операндов необходимо выдавать ошибку в консоль. 
Позволяет добавлять клиентов. Клиентам можно добавлять сервисы. Сервис одного типа может быть добавлен только один раз. Для сервиса клиента можно задавать несколько расчетных значений.  Начисление рассчитывается как сумма расчетных значений  по каждому сервису вычисленных с помощью операции сервиса.
Коммандный интерфейс:
service <name> <operation>  [parameter1, parameter2, …] – определение сервиса
	name – название
	operation – операция
	parameter1, parameter2, … - параметры
client <name> - определение клиента
	name – название
add_client_service <client_name> <service_name>
	client_name – имя клиента
	service_name - сервис
consumption <client_name> <service_name> <value> - задание расчетного значения для сервиса
	client_name – имя клиента
	service_name – сервис
value - значение
bill <client_name> - посчитать клиенту начисление
services  - выводит все определенные сервисы
clients – выводит всех клиентов, с определенными для них сервисами
Пример работы:
>service water multiply 5.5
Defined service water with multiply factor 5.5
>service tax constant 400
Defined service tax with constant value 400
>client ivan
Defined client ivan
>add_client_service ivan water
Added service water to ivan
>add_client_service ivan tax
Added service tax to ivan
>consumption ivan water 100
Added consumption 100 by service water to ivan
>bill ivan
Amount for ivan = 950
>services
service water with multiply factor 5.5
service tax with constant value 400
>clients
client ivan, services: water,tax
Требования к реализации
1.	Проекта должен быть оформлен с помощью средств автоматизации сборки проекта (Maven, Ant, …)
2.	Результат должен содержать исходники, bat/sh скрипт для запуска приложения.
3.	В случае неоднозначного описания требования, исполнитель может трактовать его в свою пользу.
