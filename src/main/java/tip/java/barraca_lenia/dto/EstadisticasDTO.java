package tip.java.barraca_lenia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstadisticasDTO {

    private List<VentaMensualDTO> ventasMensuales;
    private List<ProductoMasVendidoDTO> productosMasVendidos;
    private List<ClienteTopDTO> clientesTop;
    private List<ComparacionVentasDTO> comparacionVentas;
    private List<VentaProductoMesDTO> ventasPorProductoMes;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VentaMensualDTO {
        private String mes;
        private Double totalVentas;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductoMasVendidoDTO {
        private String nombreProducto;
        private Integer cantidadTotal;
        private Double totalVentas;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClienteTopDTO {
        private String nombreCliente;
        private Integer cantidadPedidos;
        private Double totalGastado;
        private String telefono;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ComparacionVentasDTO {
        private String mes;
        private Double totalVentas;
        private Integer cantidadPedidos;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VentaProductoMesDTO {
        private String mes;
        private String producto;
        private Double kilosVendidos;
    }

}
