// given first.hasCode() + last.hashCode() + middle.hasCode9) + DoB
//
// How do we check for nulls?
//
public int hashCode() { // overriding Object's hashCode method, this one runs better
    return Objects.hash ( first, last, middle) + DoB;
}

boolean Object.equals( Object a, Object b )
    // Don't need to do null check for this?
    // it will use a's equals method and do comparison on 
