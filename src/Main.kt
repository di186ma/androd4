// Класс для хранения полного имени
data class FullName(
    val lastName: String,
    val firstName: String,
    val middleName: String
)

// Класс для хранения адреса
data class Address(
    val address: String
)

// Класс для хранения номера телефона
data class PhoneNumber(
    val number: String
)

// Класс для хранения даты и заметки
data class DateNote(
    val day: Int,
    val month: Int,
    val year: Int,
    val note: String
)

// Основной класс для хранения информации о человеке
data class PersonInfo(
    val tabNumber: Int,
    val fullName: FullName?,
    val address: Address?,
    val phoneNumber: PhoneNumber?,
    val dateNote: DateNote?
)

// Интерфейс для ввода и вывода данных
interface DataIO {
    fun inputData(): PersonInfo
    fun outputData(person: PersonInfo)
}

// Интерфейс для сортировки по фамилии
interface Sortable {
    fun sortByLastName(personList: MutableList<PersonInfo>): List<PersonInfo>
}

// Интерфейс для поиска по ключевому слову
interface Searchable {
    fun searchByKeyword(personList: List<PersonInfo>, keyword: String): List<PersonInfo>
}

// Реализация интерфейса DataIO
class ConsoleDataIO : DataIO {
    override fun inputData(): PersonInfo {
        // Реализация ввода данных из консоли
        // Здесь предполагается, что данные вводятся корректно
        println("Введите табельный номер:")
        val tabNumber = readLine()?.toIntOrNull() ?: 0

        println("Введите фамилию:")
        val lastName = readLine() ?: ""

        println("Введите имя:")
        val firstName = readLine() ?: ""

        println("Введите отчество:")
        val middleName = readLine() ?: ""

        println("Введите адрес:")
        val address = readLine() ?: ""

        println("Введите номер телефона:")
        val phoneNumber = readLine() ?: ""

        println("Введите дату (день месяц год):")
        val day = readLine()?.toIntOrNull() ?: 0
        val month = readLine()?.toIntOrNull() ?: 0
        val year = readLine()?.toIntOrNull() ?: 0

        println("Введите заметку:")
        val note = readLine() ?: ""

        return PersonInfo(
            tabNumber,
            FullName(lastName, firstName, middleName),
            Address(address),
            PhoneNumber(phoneNumber),
            DateNote(day, month, year, note)
        )
    }

    override fun outputData(person: PersonInfo) {
        println("Табельный номер: ${person.tabNumber}")
        person.fullName?.let {
            println("Фамилия: ${it.lastName}")
            println("Имя: ${it.firstName}")
            println("Отчество: ${it.middleName}")
        }
        person.address?.let {
            println("Адрес: ${it.address}")
        }
        person.phoneNumber?.let {
            println("Телефон: ${it.number}")
        }
        person.dateNote?.let {
            println("Дата: ${it.day}.${it.month}.${it.year}")
            println("Заметка: ${it.note}")
        }
    }
}

// Реализация интерфейса Sortable
class PersonSorter : Sortable {
    override fun sortByLastName(personList: MutableList<PersonInfo>): List<PersonInfo> {
        return personList.sortedWith(compareBy { it.fullName?.lastName ?: "" })
    }
}

// Реализация интерфейса Searchable
class PersonSearcher : Searchable {
    override fun searchByKeyword(personList: List<PersonInfo>, keyword: String): List<PersonInfo> {
        return personList.filter { person ->
            person.dateNote?.note?.contains(keyword, ignoreCase = true) == true
        }
    }
}

// Пример использования
fun main() {
    val io = ConsoleDataIO()
    val sorter = PersonSorter()
    val searcher = PersonSearcher()

    val persons = mutableListOf<PersonInfo>()

    // Ввод данных
    for (i in 1..3) {
        println("Ввод данных для человека №$i")
        val person = io.inputData()
        persons.add(person)
    }

    // Сортировка по фамилии
    val sortedPersons = sorter.sortByLastName(persons)
    println("\nОтсортированный список:")
    sortedPersons.forEach { io.outputData(it) }

    // Поиск по ключевому слову
    println("\nВведите ключевое слово для поиска:")
    val keyword = readLine() ?: ""
    val foundPersons = searcher.searchByKeyword(persons, keyword)
    println("\nНайденные записи:")
    foundPersons.forEach { io.outputData(it) }
}