package com.grupo2.pokemon.implementacion;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.grupo2.pokemon.Pokemon;
import com.grupo2.pokemon.interfaz.Repositorio;

import java.util.List;
import java.util.Optional;

public class RepositorioSQLite extends SQLiteOpenHelper implements Repositorio<Pokemon> {

    private static final String DATABASE_NAME = "Pokemon.db";
    private static final int DATABASE_VERSION = 1;

    public RepositorioSQLite(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public RepositorioSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + PokemonPregunta.EntradaPregunta.NOMBRE_TABLA +
                " ("
                + PokemonPregunta.EntradaPregunta._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PokemonPregunta.EntradaPregunta.NOMBRE + " TEXT NOT NULL,"
                + PokemonPregunta.EntradaPregunta.FOTO + " TEXT NOT NULL,"
                + "UNIQUE (" + PokemonPregunta.EntradaPregunta.NOMBRE + "))");

        Pokemon pokemon = new Pokemon();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    @SuppressLint("Range")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Optional<Pokemon> get(long id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(
                PokemonPregunta.EntradaPregunta.NOMBRE_TABLA,
                null,
                "id=?",
                new String[]{id+""},
                null,
                null,
                null
        );
        String nombre=null, foto=null;
        Pokemon pokemon=null;
        while(c.getCount() > 0 && c.moveToNext()){
            nombre = c.getString(c.getColumnIndex(PokemonPregunta.EntradaPregunta.NOMBRE));
            foto = c.getString(c.getColumnIndex(PokemonPregunta.EntradaPregunta.FOTO));
        }

        if(nombre !=null && foto !=null)
            pokemon = new Pokemon();
        return Optional.of(pokemon);
    }

    @Override
    public List<Pokemon> getAll() {
        return null;
    }

    @Override
    public void update(Pokemon pokemon) {

    }

    @Override
    public void delete(Pokemon pokemon) {

    }


    @Override
    public void save(Pokemon pokemon) {
        this.save(pokemon, null);
    }
    // creamos nuevo método save() que se usa internamente en el onCreate
    private void save(Pokemon pokemon, SQLiteDatabase db) {
        if(db == null)
            db = getWritableDatabase();
        // Contenedor de valores
        ContentValues values = new ContentValues();
        // Pares clave-valor
        values.put(PokemonPregunta.EntradaPregunta.NOMBRE, pokemon.getName());
        values.put(PokemonPregunta.EntradaPregunta.FOTO, pokemon.getDescription());
        // Insertar...
        db.insert(PokemonPregunta.EntradaPregunta.NOMBRE_TABLA, null, values);
    }
}

class PokemonPregunta {
    /* contructor privado parar que no se pueda instanciar la clase
    accidentalmente */
    private PokemonPregunta() {}
    public static class EntradaPregunta implements BaseColumns {
        public static final String NOMBRE_TABLA = "Pokemon";
        public static final String NOMBRE = "nombre";
        public static final String FOTO = "foto";
        public static final String DESCRIPCION = "descripcion";
    }
}
