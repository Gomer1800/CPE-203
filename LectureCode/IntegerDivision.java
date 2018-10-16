public class IntegerDivision {
    public static void main (String[] args) {
        int i, j, k;
        double x, y, z;

        i=3;
        j=2;
        x=3.0;
        y=2.0;

        k = i/j;
        System.out.println("1 : "+k);
        z = i/j;
        System.out.println("2 : "+z);
        k = (int)i/j;
        System.out.println("3 : "+k);
        z = x/j;
        System.out.println("4 : "+z);
        z = x + i/j;
        System.out.println("5 : "+z);
        z = x + x/j;
        System.out.println("6 : "+z);
        k = (int)(x + x/j);
        System.out.println("7 : "+k);
        z = i/j * x;
        System.out.println("8 : "+z);
        z = x * i/j;
        System.out.println("9 : "+z);
        z = 4/i;
        System.out.println("10 : "+z);
        z = 4;
        System.out.println("11 : "+z);
        z = z/i * 2;
        System.out.println("12 : "+z);
        z = 1.0 * i/j;
        System.out.println("13 : "+z);
        z = 2.0/i;
        System.out.println("14 : "+z);
        z = (double)i/j;
        System.out.println("15 : "+z);
        z = (double)(i/j);
        System.out.println("16 : "+z);
        z = i/j + x + i/j;
        System.out.println("17 : "+z);
        z = i/j + x * i/j;
        System.out.println("18 : "+z);
        z = i/j + x + i/(double)j;
        System.out.println("19 : "+z);
        z = x + y * 4.0;
        System.out.println("20 : "+z);
        z = (x + y) * 4.0;
        System.out.println("21 : "+z);
        z = x * y + 4.0;
        System.out.println("22 : "+z);
        z = 2.0/i;
        System.out.println("23 : "+z);
        z = x/j;
        System.out.println("24 : "+z);
    }
}
