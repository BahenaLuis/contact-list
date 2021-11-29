# contact-list
Administrar lista de contactos


Site url: https://contact-list-pinnacle.herokuapp.com

Users for test:

- usertest_uno@gmail.com
- userTest2021

<br>

- usertest_dos@gmail.com
- userTest2021

<br>

- usertest_tres@gmail.com
- userTest2021

<br>

API Endpoints

Todas las peticiones requieren autenticación basica, con usuario y contraseña.

<br>

- Crea un nuevo contacto
- POST -> https://contact-list-pinnacle.herokuapp.com/api/contact
- Recibe un body con la información del contacto
- Payload de ejemplo (nombre y teléfono requeridos)
<br>
{
    "name": "Francisco Mendez",
    "phone": "6311245785",
    "age": 32,
    "nickname": "Pancho"
}

<br>

- Obtiene un listado de contactos ligado al usuario
- GET -> https://contact-list-pinnacle.herokuapp.com/api/contact
- Podemos pasar como parametros la pagina, el tamaño de ella, el campo y dirección de ordenamiento
- Ejemplo: https://contact-list-pinnacle.herokuapp.com/api/contact?page=0&size=5&sort=name,desc


<br>

- Obtener los datos de un contacto por id
- GET -> https://contact-list-pinnacle.herokuapp.com/api/contact/{id}

<br>

- Actualizar los datos de un contacto por id
- PUT -> https://contact-list-pinnacle.herokuapp.com/api/contact/{id}
- Recibe un body con la información a modificar del contacto
- Payload de ejemplo (ningún campo es requerido)
<br>
{
    "name": "Francisco Mendez",
    "phone": "6311245785",
    "age": 32,
    "nickname": "Pancho"
}

<br>

- Eliminar un contacto mediante su id
- DELETE -> https://contact-list-pinnacle.herokuapp.com/api/contact/{id}

<br>

