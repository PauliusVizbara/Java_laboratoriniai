/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mano;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import laborai.studijosktu.HashType;
import laborai.studijosktu.MapADTp;
import laborai.studijosktu.MapKTU;

/**
 *
 * @author Paulius
 */
public class MapKTUOA<K, V> implements MapADTp<K, V> {

    private final Entry EMPTY_ENTRY = new Entry();
    public static final int DEFAULT_INITIAL_CAPACITY = 16;
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;
    public static final HashType DEFAULT_HASH_TYPE = HashType.DIVISION;

    // Maišos lentelė
    protected Entry<K, V>[] table;
    // Lentelėje esančių raktas-reikšmė porų kiekis
    protected int size = 0;
    // Apkrovimo faktorius
    protected float loadFactor;
    // Maišos metodas
    protected HashType ht;
    //--------------------------------------------------------------------------
    //  Maišos lentelės įvertinimo parametrai
    //--------------------------------------------------------------------------
    // Maksimalus suformuotos maišos lentelės grandinėlės ilgis
    protected int maxChainSize = 0;
    // Permaišymų kiekis
    protected int rehashesCounter = 0;
    // Paskutinės patalpintos poros grandinėlės indeksas maišos lentelėje
    protected int lastUpdatedChain = 0;
    // Lentelės grandinėlių skaičius     
    protected int chainsCounter = 0;
    // Einamas poros indeksas maišos lentelėje
    protected int index = 0;

    /* Klasėje sukurti 4 perkloti konstruktoriai, nustatantys atskirus maišos 
     * lentelės parametrus. Jei kuris nors parametras nėra nustatomas - 
     * priskiriama standartinė reikšmė.
     */
    public MapKTUOA() {
        this(DEFAULT_HASH_TYPE);
    }

    public MapKTUOA(HashType ht) {
        this(DEFAULT_INITIAL_CAPACITY, ht);
    }

    public MapKTUOA(int initialCapacity, HashType ht) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR, ht);
    }

    public MapKTUOA(float loadFactor, HashType ht) {
        this(DEFAULT_INITIAL_CAPACITY, loadFactor, ht);
    }

    public MapKTUOA(int initialCapacity, float loadFactor, HashType ht) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }

        if ((loadFactor <= 0.0) || (loadFactor > 1.0)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }

        this.table = new Entry[initialCapacity];
        this.loadFactor = loadFactor;
        this.ht = ht;
    }

    /**
     * Patikrinama ar atvaizdis yra tuščias.
     *
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Grąžinamas atvaizdyje esančių porų kiekis.
     *
     * @return Grąžinamas atvaizdyje esančių porų kiekis.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Išvalomas atvaizdis.
     */
    @Override
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
        index = 0;
        lastUpdatedChain = 0;
        maxChainSize = 0;
        rehashesCounter = 0;
        chainsCounter = 0;
    }

    /**
     * Patikrinama ar pora egzistuoja atvaizdyje.
     *
     * @param key raktas.
     * @return Patikrinama ar pora egzistuoja atvaizdyje.
     */
    @Override
    public boolean contains(K key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && table[i].equals(value)) {
                return true;
            }
        }
        return false;
    }

    public boolean removeAt(int index) {
        if (table[index] != null) {
            table[index] = null;
            return true;
        }

        return false;

    }

    public V putIfAbsent(K key, V value) {
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                continue;
            }

            if (table[i].key == key) {
                return table[i].value;
            }

        }

        put(key, value);
        return null;
    }

    public int emptyElements() {

        return table.length - chainsCounter;
    }

    public Set<K> keySet() {
        Set<K> set = new HashSet<K>();

        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                continue;
            }

            set.add(table[i].key);

        }
        return set;
    }

    public void putAll(MapKTU<K, V> newMap) {

    }

    /**
     * Atvaizdis papildomas nauja pora.
     *
     * @param key raktas,
     * @param value reikšmė.
     * @return Atvaizdis papildomas nauja pora.
     */
    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value is null in put(Key key, Value value)");
        }
        
        index = findPosition(key,true);
        //System.out.println(index);
        if (index == -1) {
            return null;
        }

        Entry<K, V> entry = table[index];
        if (entry == null) {
            table[index] = new Entry<>(key, value);
            size++;

            if (size > table.length * loadFactor) {
                rehash(table[index]);
            } else {
                lastUpdatedChain = index;
            }
        } else {
            entry.value = value;
            lastUpdatedChain = index;
        }

        return value;
    }

    private int findPosition(K key, boolean isLookingForEmpty) {
        int index = hash(key, ht);
        int indexO = index;
        int i = 0;
        
        for (int j = 0; j < table.length; j++) {
            if (isLookingForEmpty) {
                if (table[index] == null || table[index] == EMPTY_ENTRY) {
                    return index;
                } else {
                    if (table[index] != null) {
                        return index;
                    }
                }
            }
            i++;
            index = (indexO + i * hash2(key)) % table.length;
        }
        return -1;
    }

    private int hash2(K key) {
        return 7 - (Math.abs(key.hashCode()) % 7);
    }

    /**
     * Grąžinama atvaizdžio poros reikšmė.
     *
     * @return Grąžinama atvaizdžio poros reikšmė.
     *
     * @param key raktas.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null in get(Key key)");
        }

        index = findPosition(key,true);
        Entry<K, V> entry = table[index];
        return (entry != null) ? entry.value : null;
    }

    /**
     * Pora pašalinama iš atvaizdžio.
     *
     * @param key Pora pašalinama iš atvaizdžio.
     * @return key raktas.
     */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null in remove(Key key)");
        }

        int index = findPosition(key,false);
        if (index != -1) {
            table[index] = EMPTY_ENTRY;
        }

        return null;
    }

    /**
     * Permaišymas
     *
     * @param entry
     */
    private void rehash(Entry<K, V> entry) {
        MapKTUOA mapKTU
                = new MapKTUOA(table.length * 2, loadFactor, ht);
        for (int i = 0; i < table.length; i++) {
            while (table[i] != null) {
                if (table[i].equals(entry)) {
                    lastUpdatedChain = i;
                }
                mapKTU.put(table[i].key, table[i].value);

            }
        }
        table = mapKTU.table;
        maxChainSize = mapKTU.maxChainSize;
        chainsCounter = mapKTU.chainsCounter;
        rehashesCounter++;
    }

    /**
     * Maišos funkcijos skaičiavimas: pagal rakto maišos kodą apskaičiuojamas
     * atvaizdžio poros indeksas maišos lentelės masyve
     *
     * @param key
     * @param hashType
     * @return
     */
    private int hash(K key, HashType hashType) {
        int h = key.hashCode();
        switch (hashType) {
            case DIVISION:
                return Math.abs(h) % table.length;
            case MULTIPLICATION:
                double k = (Math.sqrt(5) - 1) / 2;
                return (int) (((k * Math.abs(h)) % 1) * table.length);
            case JCF7:
                h ^= (h >>> 20) ^ (h >>> 12);
                h = h ^ (h >>> 7) ^ (h >>> 4);
                return h & (table.length - 1);
            case JCF8:
                h = h ^ (h >>> 16);
                return h & (table.length - 1);
            default:
                return Math.abs(h) % table.length;
        }
    }

    /**
     * Paieška vienoje grandinėlėje
     *
     * @param key
     * @param entry
     * @return
     */
    @Override
    public String[][] toArray() {
        String[][] result = new String[table.length][];
        int count = 0;
        for (Entry<K, V> n : table) {
            String[] list = new String[getMaxChainSize()];
            int countLocal = 0;
            result[count] = list;
            count++;
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Entry<K, V> entry : table) {
            if (entry != null) {
                result.append(entry.toString()).append(System.lineSeparator());

            } else {
                result.append("Entry equals null").append(System.lineSeparator());
            }
        }
        return result.toString();
    }

    /**
     * Grąžina maksimalų grandinėlės ilgį.
     *
     * @return Maksimalus grandinėlės ilgis.
     */
    @Override
    public int getMaxChainSize() {
        return maxChainSize;
    }

    /**
     * Grąžina formuojant maišos lentelę įvykusių permaišymų kiekį.
     *
     * @return Permaišymų kiekis.
     */
    @Override
    public int getRehashesCounter() {
        return rehashesCounter;
    }

    /**
     * Grąžina maišos lentelės talpą.
     *
     * @return Maišos lentelės talpa.
     */
    @Override
    public int getTableCapacity() {
        return table.length;
    }

    /**
     * Grąžina paskutinės papildytos grandinėlės indeksą.
     *
     * @return Paskutinės papildytos grandinėlės indeksas.
     */
    @Override
    public int getLastUpdatedChain() {
        return lastUpdatedChain;
    }

    /**
     * Grąžina grandinėlių kiekį.
     *
     * @return Grandinėlių kiekis.
     */
    @Override
    public int getChainsCounter() {
        return chainsCounter;
    }

    @Override
    public double averageChainLength() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected class Entry<K, V> {

        // Raktas        
        protected K key;
        // Reikšmė
        protected V value;

        protected Entry() {
        }

        protected Entry(K key, V value) {
            this.key = key;
            this.value = value;

        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

}
