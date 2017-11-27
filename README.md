Instrucciones para uso:
-Se necesita tener instalado java 1.8

-Por defecto la aplicación está desiñada para:
Si la primera vez que corres la aplicación en tu sistema y no encuentra la base
de datos, la aplicación la creará pero al cerrarla se eliminaran todos los datos.

Si desdeas poder almacenar los datos permentemente, sigue los sguientes pasos:
1.-Al abrir el proyecto en netbeans, del lado izquierdo, en donde se ve la jerarquia del proyecto,
buscar la pestaña "Files" y despues seguir la siguiente ruta:
-src
---main
-----resources
---------META-INF
y entar en el archivo persistence.xml para modificar la siguiente propiedad
de no poder seguir la ruta, buscar la carpeta META-INF para encontrar el
archivo persistence.xml

<property name="hibernate.hbm2ddl.auto" value="create"/>

donde deberias modificar el "create" a "validate"

-Para acceder al sistema tienes que usar como
Nombre de usuario: Javier Montoya
Contraseña: o
