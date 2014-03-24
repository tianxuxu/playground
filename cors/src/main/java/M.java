import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class M {

	public static void main(String[] args) {
		String s1 = "one";
		String s2 = "two";

		String s3 = String.join(",", s1, s2);
		System.out.println(s3);

		System.out.println(Base64.getUrlEncoder().encodeToString("aaaa".getBytes()));

		List<Integer> s = Arrays.asList(1, 2, 3, 4);

		s.stream().filter(x -> x > 2).forEach(x -> System.out.println(x));

		List<String> l = Arrays.asList("a", "bb", "ccc", "dddd");

		LocalDate ld = LocalDate.now();
		YearMonth ym = YearMonth.now();
		System.out.println(ym);
	}

}
