//http://www.tutorialspoint.com/java/java_serialization.htm


public class Serialization {
	Employee e1 = new Employee();

	serialize(Employee);

	Employee e2 = (Employee)deserialize();

	public void serialize(object o) {
		// do serialization here
	}

	public void deserialize(object o) {
		// do deserialization here
	}

}



	
public class Employee implements java.io.Serializable {
	private static final long serialVersionUID
	// dili ni ma apil ug serialize
	private transient int SSN;  

	// if mu butang kag object kay dapat nag implement pud cya ug Serialable 
	private Person person; 

}


/*
	Externalization = same ra gihapon sa Serialization pero dili naka kelangan mu butang ug transcient 
		2 default methods: 
			- writeExternal
			- readExternal

*/


class Employee implements java.io.Externalizable {

	@Override
	public void writeExternal(ObjectOutput out) throws IOExcpetion{
	
	}	

	@Override
	public void readExternal(ObjectInput in) throws IOExcpetion{
	
	}
}



What is serialization???
	http://stackoverflow.com/questions/2232759/what-is-the-purpose-of-serialization-in-java

	Serialable is a marker interface
		it marks (signals) JVM that it does not do anything

	transient variable = excludes variable to be serialized


Externalization

	more control than of what u want to save rather than serialization

What is the use of serialVersionUID?
	for verification purposes 

	what happends if u have the same class, same serialVersionUID but different fields as its variables? will the serialize


	everytime u change the object u have to change the serialVersionUID

		serialVersionUID marks the version of ur object

	what if u do not provide a serialVersionUID?
		java produce own serialVersionUID by using the SHA-1 algorithm 
		The serialVersionUID is computed using the signature of a stream of bytes that reflect the class definition. 
		The National Institute of Standards and Technology (NIST) Secure Hash Algorithm (SHA-1) 

		http://docs.oracle.com/javase/6/docs/platform/serialization/spec/class.html#4100
		http://www.mkyong.com/java-best-practices/understand-the-serialversionuid/





