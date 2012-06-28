package lambdaj;

public class UserAgeComparator implements java.util.Comparator<User> {

	@Override
	public int compare(final User o1, final User o2) {
		return o1.getAge() - o2.getAge();
	}

}
