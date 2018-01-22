package com.ut3.ehg.turismotepic.db;

/**
 * Created by LAB-DES-05 on 27/09/2016.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class db_pois extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="turismo.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "pois";
    public static final String COLUMN_NAME_ID = "id_poi";
    public static final String COLUMN_NAME_ID_CATEGORIA = "id_categoria";
    public static final String COLUMN_NAME_POI = "nombre";
    public static final String COLUMN_NAME_SHORT_DESCRIPCION = "descripcion_corta";
    public static final String COLUMN_NAME_LONG_DESCRIPCION = "descripcion_larga";
    public static final String COLUMN_NAME_LAT = "latitud";
    public static final String COLUMN_NAME_LON = "longitud";
    public static final String COLUMN_NAME_HORARIO = "horario";
    public static final String COLUMN_NAME_CALF = "calificacion";
    public static final String COLUMN_NAME_COSTO = "costo";
    public static final String COLUMN_NAME_COSTOAPROX = "costo_aprox";
    public static final String COLUMN_NAME_FOTO = "foto";


    //--------------------------------Insert Table Query
    public static final String SQL_INSERT_ENTRIES = "INSERT INTO `pois` (`id_poi`, `id_categoria`, `nombre`,`descripcion_larga`,`latitud`, `longitud`, `horario`, `calificacion`, `costo`, `foto`) " +
            "VALUES " +
            "(01,3,'Catedral ','La catedral de Tepic, asiento y cátedra del Obispo, es considerado uno de los edificios más importantes del centro historico, representa el símbolo religioso y civil de esta ciudad. En el interior de la nave se despliegan las galas renacentistas en bellas formas clásicas. El piso del coro es sostenido por seis arcos de medio punto que descansan en un cornisón y columnas de estilo corintio, existe además, otro arco más pequeño con las mismas características.','21.512026','-104.891528','7:00 a 21:00 hrs','0','Entrada libre','f01_01'),"+
            "(02,1,'Hotel Fray Junípero Serra ','El Hotel Fray Junípero Serra está ubicado frente a Catedral y junto a la Plaza Principal. Mejor conocido por la comunidad local como “El Fray”, es reconocido por una larga historia de calidad en el servicio, lo que lo ha posicionado como el hotel de referencia en la ciudad.','21.509080','-104.891909','10:00-14:00 hrs, 16:00-19:00hrs','0','Variable','f02_01'),"+
            "(03,4,'Casa Museo de Juan Escutia ','En la Casa Museo de Juan Escutia, se exponen objetos personales de éste héroe nayarita, sus condecoraciones militares, pinturas y cuadros alusivos a la batalla de Chapultepec. Este museo funciona con una exposición permanente donde se representa la vida del cadete y su lucha por la defensa de nuestra bandera nacional, cuenta con un pequeño foro con capacidad para 40 personas.','21.510455','-104.890799','10:00-14:00 hrs, 16:00-19:00hrs','0','Entrada libre','f03_01'),"+
            "(04,4,'Casa Mueso de Amado Nervo ','Está ubicado en una casona de mediados del siglo XIX, en la cual nació el poeta Amado Nervo el 27 de agosto de 1870. El inmueble fue habilitado como museo e inaugurado el 27 de abril de 1970. Su restauración corrió a cargo del arquitecto Marco Antonio Rentería Jardón en 1999. En la Casa Museo de Amado Nervo se muestran fotografías, objetos personales, documentos y algunas de sus famosas poesías.','21.512446','-104.890477','10:00-14:00 hrs, 16:00-19:00hrs','0','Entrada libre','f04_01'),"+
            "(05,3,'Péergola (Plaza principal) ','Fue a inicios del siglo XX cuando al paisaje de la plaza del centro histórico se adicionó la Columna de la Pacificación, se edificó en 1834, por órdenes del presidente de la república Sebastián Lerdo de Tejada, para conmemorar el triunfo del ejército federal sobre la rebelión lozadeña. Alrededor de 1952 se destruyó el kiosco y los jardines, pero se instaló la actual fuente de los delfines y la pérgola de estilo eclético del siglo XX, y una segunda fuente, la Fuente de las Ranas.','21.512184','-104.892335','Libre acceso','0','Libre acceso','f05_01'),"+
            "(06,3,'Fuente de las ranas (Plaza principal) ','En 1952 se instaló la actual fuente de delfines y una segunda fuente, la Fuente de las Ranas.','21.512084','-104.892021','Libre acceso','0','Libre acceso','f06_01'),"+
            "(07,3,'Fuente de Delfines (Plaza principal) ','En 1952 se instaló la actual fuente de delfines y una segunda fuente, la Fuente de las Ranas.','21.512256','-104.892497','Libre acceso','0','Libre acceso','f07_01'),"+
            "(08,4,'Escuela Superior de Música ','Inmueble de estilo neoclásico de principios del siglo XX y que actualmente es la Escuela Superior de Música de Nayarit, la cual fue creada hace 16 años.','21.510975','-104.890382','16:00-19:00','0','Variable','f08_01'),"+
            "(09,4,'Casa Fenelón ','La casa Fenelón fue construida a finales del siglo XVIII y la primer sede del Poder Ejecutivo Estatal de Nayarit. Ubicada en la calle Lerdo #71 oriente, a escasa cuadra y media de la plaza principal y de la catedral, forma parte del patrimonio de la Universidad Autónoma de Nayarit (UAN). Fue también asiento del Instituto de Ciencias y Letras de Nayarit y de la Escuela de Medicina Humana.','21.511076','-104.890595','Sin acceso','0','Sin acceso','f09_01'),"+
            "(10,3,'Busto de Carranza (Plaza antigua) ','Ubicado en la plaza localizada en la calle Zacatecas, entre Miguel Hidalgo y Lerdo; este monumento hace alusión a Venustiano Carranza Expresidente quien gestó una nueva Constitución promulgada el 5 de febrero de 1917.','21.510718','-104.891028','Libre acceso','0','Libre acceso','f10_01'),"+
            "(11,4,'Museo Regional INAH','La construcción del Museo Regional de Antropología e Historia data del siglo XVII. En este museo se exhiben auténticas figuras arqueológicas, entre las que se encuentran piedras de forma semicircular con grabados representando al águila devorando a la serpiente; además, se exhiben objetos de obsidiana, piedra y cobre. Con el paso de los lustros, el edificio fue pasando a manos de distintos propietarios, sin perder su carácter de casa habitación. Es a fines del siglo XIX cuando es adquirida por la Casa Comercial Delius, albergando en su planta baja las oficinas del Consulado Alemán, que atendía asuntos diplomáticos, financieros y comerciales, mientras que la planta alta servía de habitación a la familia del cónsul Maximiliano Delius, hasta 1930.','21.509988','-104.892426','9:00 - 18:00','0','50','f11_01'),"+
            "(12,10,'Oficinas regionales del INAH ','Oficinas regionales del Instituto Nacional de Antropología e Historia, Ubicadas en Lerdo 76 de la capital. El Instituto Nacional de Antropología e Historia (INAH) investiga, conserva y difunde el patrimonio arqueológico, antropológico, histórico y paleontológico de la nación y del estado con el fin de fortalecer  la identidad y memoria de la sociedad que lo detenta.','21.510878','-104.890568','9:00 - 18:00','0','Entrada libre','f12_01'),"+
            "(13,10,'CECUPI','Albergado en la planta baja del edificio histórico conocido como Ex-Hotel Palacio (1923), el cual fue también casa habitación de la familia Forbes y sede del Consulado Británico (1827). La creación de este Museo obedeció a la necesidad de reconocer la creatividad y las iniciativas culturales de los sectores populares de Nayarit. El edificio cuenta con dos salas de exposición temporal, un museo de sitio y la exposición permanente dedicada a los cinco grupos culturales del estado.','21.510285','-104.892396','10:00-14:00 hrs, 16:00-19:00hrs','0','Entrada libre','f13_01'),"+
            "(14,10,'Centro de Arte Contemporáneo del Bicentenario Emilia Ortíz ','Esta finca de estilo neoclásico típica de la ciudad fue construida totalmente de adobe a finales del siglo XIX. La Casa Aguirre, considerada monumento histórico por el Instituto de Antropología e Historia, es un edificio de características eclécticas, construida en dos niveles. Este espacio se remodeló recientemente con el apoyo de CONACULTA a través del programa PAICE y funciona como espacio de exposiciones artísticas de la plástica nayarita, contempla un taller de grabado y se llevan a cabo presentaciones de libros, ruedas de prensa, conferencias y talleres en su sala audiovisual de primer nivel','21.510912','-104.891872','10:00-14:00 hrs, 16:00-19:00hrs','0','Entrada libre','f14_01'),"+
            "(15,9,'El cerro de la Cruz ','El Cerro de la Cruz es un cerro con dos opciones para subirlo,a travez de un camino empedrado con acera, o a travez de un camino escabroso con varias veredas de diferentes dificultades. A lo largo del camino empedrado existen 14 cruces que representan cada una de las 14 estaciones del Viacrucis. En la cima del cerro hay una pequeña explanada, y sobre ésta se encuentran 3 cruces; la más grande tiene una figura de Jesucristo y las otras 2 son más pequeñas.','21.533983','-104.883625','6:00-20:00 hrs','0','Entrada libre','f15_01'),"+
            "(16,9,'Parque La Loma ','La Loma, acotada por las calles Insurgentes, Veracruz y Niños Héroes, fue el sitio en el que acampó el insurgente José María Mercado en 1810 y el escenario de la victoria de Luis Correa sobre las fuerzas del insurrecto García en 1823. El espacio se recorre por una calzada bordeada de amapas y palmeras; sendos monumentos consagran la memoria del poeta Amado Nervo, al general Esteban Baca Calderón y los caídos durante la Revolución Mexicana, Actualmente tiene, campos deportivos y un teatro al aire libre.','21.504015','-104.899448','Todo el día','0','Entrada libre','f16_01'),"+
            "(17,9,'La Alameda ','La Alameda es un parque público, que se remonta a los primeros años del siglo XIX. Cuenta con, laureles de la India, una fuente neoclásica. estatuas que representan estaciones del año y pequeños espejos de agua surtidos por figuras de cisnes. En su perímetro tiene, una verja sólo interrumpida por las columnas toscanas de cantera gris que marcan los accesos','21.510335','-104.900737','Todo el día','0','Entrada libre','f17_01'),"+
            "(18,10,'Estadio Arena Cora ','El Estadio Arena Cora es un estadio multifuncional ubicado a las afueras de la ciudad de Tepic. Es utilizado principalmente para partidos de fútbol y es sede del Club Deportivo Tepic, equipo de la Liga de Ascenso de México. El estadio tiene capacidad para 12,945 espectadores en su primera etapa.','21.492419','-104.801467','Por evento','0','Variable','f18_01'),"+
            "(19,10,'Palacio de Gobierno ','Edificio que fue construido entre 1870 y 1885 por el maestro Gabriel Luna Rodríguez, en un proyecto que pretendía ser la penitenciaría dela ciudad. Su fachada muestra una composición en la que se alternan ventanales y puertas de la planta baja con balcones en la planta alta. La parte central del recinto se remata con un amplio frontón en el que hay un reloj y en el interior un conjunto de siete naves de bóveda de cañón que se unen al centro de un pequeño patio, disposición que pertenece al típico estilo de las construcciones carcelarias de la época. Actualmente es la sede del gobierno del estado de Nayarit','21.507507','-104.893879','9:00 - 15:00 hrs','0','Entrada libre','f19_01'),"+
            "(20,9,'Plaza Bicentenario ','Plaza Bicentenario es el espacio que se localiza frente al Palacio de Gobierno.este espacio correspondió a la manzana número 120 en la segunda mitad del siglo XIX. Fue comprado por 500 pesos y donado a la ciudad en 1870 por el primer jefe político del Distrito Militar de Tepic Juan de Sanromán, con la finalidad de darle un mayor atractivo visual a la entonces Penitenciaria, hoy sede del Poder Ejecutivo del Estado.','21.507188','-104.893074','Todo el día','0','Entrada libre','f20_01'),"+
            "(21,8,'Plaza Forum ','Centro comercial cuenta con la presencia de grandes tiendas departamentales de prestigio.','21.491959','-104.865983','11:00 - 21:00 hrs','0','Entrada libre','f21_01'),"+
            "(22,10,'Central de Autobuses de Tepic ','Gran flujo de pasajeros de Sinaloa a Jalisco. Ofrece diversas líneas que operan en la terminal.','21.498444','-104.88677','Todo el día','0','Variable','f22_01'),"+
            "(23,5,'Banco Santander Catedral ','Banco Santander ubicado al lado de la Catedral de Tepic.','21.512399','-104.891343','8:30 - 16:00 hrs','0','Entrada libre','f23_01'),"+
            "(24,5,'Banco BBVA Bancomer ','Banco BBVA Bancomer ubicado en Av. México.','21.510618','-104.892312','8:30 - 16:00 hrs','0','Entrada libre','f24_01'),"+
            "(25,5,'Banco Banamex ','Banco Banamex ubicado en Av. México.','21.509944','-104.892264','9:00 - 16:00 hrs','0','Entrada libre','f25_01'),"+
            "(26,5,'Banco Banorte ','Banco Banorte ubicado en Av. México.','21.512819','-104.892119','8:30 - 16:00 hrs','0','Entrada libre','f26_01'),"+
            "(27,5,'Banco HSBC ','Banco HSBC ubicado en la plaza principal de Tepic.','21.512194','-104.892861','8:30 - 16:00 hrs','0','Entrada libre','f27_01'),"+
            "(28,5,'Scotiabank ','Banco Scotiabank ubicado en calle Hidalgo.','21.511046','-104.892325','8:30 - 16:00 hrs','0','Entrada libre','f28_01'),"+
            "(29,7,'Liverpool ','Tiendas departamentales enfocadas al consumidor de ingreso medio y alto.','21.491712','-104.865276','11:00 - 21:00 hrs','0','Entrada libre','f29_01'),"+
            "(30,7,'Oxxo ','Tienda de autoservicio.','21.511403','-104.892637','Todo el día','0','Entrada libre','f30_01'),"+
            "(31,7,'Salinas & Rocha ','Empresa dedicada a la venta de muebles y accesorios para el hogar.','21.511058','-104.892761','9:00 - 21:00 hrs','0','Entrada libre','f31_01'),"+
            "(32,7,'Modatelas ',' Telas para Vestir y Adornar tu Hogar así como una Gran Cantidad de Productos de Mercería, Confección.','21.511106','-104.892974','8:00 - 21:00 hrs','0','Entrada libre','f32_01'),"+
            "(33,7,'Coppel Canada México ','Es una cadena comercial de tiendas departamentales de ventas a través del otorgamiento de créditos.','21.510821','-104.892217','8:00 - 21:00 hrs','0','Entrada libre','f33_01'),"+
            "(34,6,'Farmacias Benavides ','Venta a detalle de productos de salud y bienestar.','21.510982','-104.892138','8:00 - 21:00 hrs','0','Entrada libre','f34_01'),"+
            "(35,6,'Farmacias El Fénix ','Venta a detalle de productos de salud y bienestar.','21.511281','-104.89175','8:00 - 21:00 hrs','0','Entrada libre','f35_01'),"+
            "(36,6,'Farmacias Sufacen ','Venta de medicinas de patente, medicamentos genéricos y medicamentos controlados.','21.512074','-104.895249','8:00 - 21:00 hrs','0','Entrada libre','f36_01'),"+
            "(37,2,'Tower Pizzas ','Cafetería con principal enfoque en pizzas.','21.509889','-104.892024','11:00 - 21:00 hrs','0','Entrada libre','f37_01'),"+
            "(38,2,'KFC ','Franquicia de restaurantes de comida rápida especializada en pollo frito.','21.511975','-104.892988','11:00 - 21:00 hrs','0','Entrada libre','f38_01'),"+
            "(39,2,'Fresh Salads ','Restaurante de ensaladas con un excelente y rápido servicio, ingredientes frescos y comida saludable.','21.511718','-104.892119','11:00 - 21:00 hrs','0','Entrada libre','f39_01'),"+
            "(40,2,'Casa Tigre ','Restaurante bufete mexicano para desayunar.','21.510665','-104.890114','8:00 - 13:00','0','115','f40_01'),"+
            "(41,10,'Sanitarios','Baños ubicados en la Presidencia Municipal.','21.512484','-104.893123','9:00 - 16:30 hrs','0','Entrada libre','f41_01'),"+
            "(42,10,'CICESE','Centro de Investigación Científica y de Educación Superior de Ensenada.','21.484181','-104.848551','8:00 - 16:30 hrs','0','Sin acceso','f42_01'),"+
            "(43,4,'Museo interactivo','El Museo Interactivo de Ciencias e Innovación de Nayarit se ubica en el parque tecnológico de esta ciudad Unidos por el Conocimiento, abarca al menos tres hectáreas y atiende entre 200 y 300 niños, niñas y adultos diariamente.','21.482005','-104.849473','8:00 - 16:30 hrs','0','','f48_01'),"+
            "(44,10,'Puerta de Hierro','El Centro Médico Puerta de Hierro Tepic inicia operaciones en septiembre de 2010.','21.480352','-104.849279','8:00 - 16:30 hrs','3','Sin acceso','f44_01')," +
            "(45,1,'Las Cabañas Grand Motel','Está ubicado en la carretera libre a Guadalajara en San Cayetano. Ofrece atención las 24 hrs. con un servicio de calidad y privacidad.','21.429734','-104.789849','24 hrs','3','','f48_01')";

















    //--------------------------------Delete Table Query
    public static final String SQL_DELETE_ENTRIES = " DROP TABLE IF EXIST " + TABLE_NAME;
    //--------------------------------Create Table Query
    public static final String CREATE_TABLE = " create table "
            + TABLE_NAME + " ( "
            + COLUMN_NAME_ID
            + " integer primary key, "
            + COLUMN_NAME_ID_CATEGORIA
            + " integer, "
            + COLUMN_NAME_POI
            + " text, "
            + COLUMN_NAME_SHORT_DESCRIPCION
            + " text, "
            + COLUMN_NAME_LONG_DESCRIPCION
            + " text, "
            + COLUMN_NAME_LAT
            + " text, "
            + COLUMN_NAME_LON
            + " text, "
            + COLUMN_NAME_HORARIO
            + " text, "
            + COLUMN_NAME_CALF
            + " text, "
            + COLUMN_NAME_COSTO
            + " text, "
            + COLUMN_NAME_COSTOAPROX
            + " text, "
            + COLUMN_NAME_FOTO
            + " text )";


    public db_pois(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(db_pois.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public ArrayList<String> obtenerPois(int cat){
        ArrayList<String> lista=new ArrayList<>();
        SQLiteDatabase db= this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM "+TABLE_NAME+" WHERE id_categoria="+cat;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_POI));
                    int id = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ID));
                    lista.add(name);
                }
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
            db.close();
        }
        return lista;
    }

    public ArrayList<String> obtenerPoisId(int cat){
        ArrayList<String> lista=new ArrayList<>();
        SQLiteDatabase db= this.getReadableDatabase();
        db.beginTransaction();
        try {
            String selectQuery = "SELECT * FROM "+TABLE_NAME+" WHERE id_categoria="+cat;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String id = Integer.toString(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ID)));
                    lista.add(id);
                }
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
            db.close();
        }
        return lista;
    }

}
