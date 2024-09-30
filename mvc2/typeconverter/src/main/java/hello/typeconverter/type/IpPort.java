package hello.typeconverter.type;

import hello.typeconverter.converter.IntegerToStringConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@Getter
@EqualsAndHashCode // 객체의 상태 값들이 모두 같으면 true 를 리턴해줌
public class IpPort {
    private String ip;
    private Integer port;

    public IpPort(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        IpPort ipPort = (IpPort) o;
//        return Objects.equals(ip, ipPort.ip) && Objects.equals(port, ipPort.port);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(ip, port);
//    }
}
