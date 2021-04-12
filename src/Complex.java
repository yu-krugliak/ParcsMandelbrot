public class Complex {
    public double R;
    public double I;

    public Complex(double r, double i) {
        R = r;
        I = i;
    }

    public void Square() {
        double tmp = R * R - I * I;
        I = 2 * R * I;
        R = tmp;
    }

    public double Magnitude()
    {
        return Math.sqrt(I * I + R * R);
    }

    public Complex Add(double r, double i) {
        R += r;
        I += i;
        return this;
    }

    public Complex Add(Complex n)
    {
        return Add(n.R, n.I);
    }

    public Complex Subtract(double r, double i) {
        R -= r;
        I -= i;
        return this;
    }

    public Complex Subtract(Complex n)
    {
        return Subtract(n.R, n.I);
    }

    public Complex Multiply(double r, double i) {
        double tmp = R * r - I * i;
        I = R * i + I * r;
        R = tmp;

        return this;
    }

    public Complex Multiply(Complex n)
    {
        return Multiply(n.R, n.I);
    }
}
