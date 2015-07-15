public V put(K key, V value) {
    if (table == EMPTY_TABLE) {
        inflateTable(threshold);
    }
    if (key == null)
        return putForNullKey(value);
    int hash = hash(key);                   // computes based on a certain algo
    int i = indexFor(hash, table.length);   // returns index for hashcode 
    for (Entry<K,V> e = table[i]; e != null; e = e.next) {
        Object k;
        if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {      // .equals and .hashCode 
            V oldValue = e.value;
            e.value = value;
            e.recordAccess(this);
            return oldValue;
        }
    }

    modCount++;
    addEntry(hash, key, value, i);
    return null;
}

.equals() and .hashCode()

    ang default i hatag sa .hashCode() kay ang memory location sa thing

    u have to override ur hashcode and equal method na in a way if dili jud equal ang object, mu fail ang condition sa line number 11 

        Example:
            Person p = new Person("Yen");
            Perosn p2 = new Person("Yen");

            sa HashSet mu make ni ug lain person sa memory heap bisan murag same ra cla na person


        