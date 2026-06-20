package tip.java.barraca_lenia.biz.dao.services;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import tip.java.barraca_lenia.biz.dao.entities.*;
import tip.java.barraca_lenia.biz.dao.repositories.*;
import tip.java.barraca_lenia.dto.ClienteAnonimoDTO;
import tip.java.barraca_lenia.dto.DetalleDTO;
import tip.java.barraca_lenia.dto.EstadisticasDTO;
import tip.java.barraca_lenia.dto.PedidoDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;
@EnableCaching
@Service
@AllArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final DireccionRepository direccionRepository;
    private final EstadoRepository estadoRepository;
    private final PresentacionRepository presentacionRepository;
    private final ClienteAnonimoService clienteAnonimoService;


    //crear pedido
    public PedidoDTO crearPedido(PedidoDTO dto, String tokenClienteAnonimo) {

        if (dto.getClienteAnonimo() != null) {
            return crearPedidoAnonimo(dto, tokenClienteAnonimo);
        }

        return crearPedidoUsuario(dto);
    }

    @CacheEvict(value = "estadisticas", allEntries = true)
    private PedidoDTO crearPedidoUsuario(PedidoDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Direccion direccion = direccionRepository.findById(dto.getIdDireccion())
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));

        Estado estado = estadoRepository.findById(dto.getIdEstado())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        Pedido pedido = new Pedido();
        pedido.setFechaPedido(LocalDate.now());
        pedido.setFechaEntrega(dto.getFechaEntrega());
        pedido.setHorarioEntrega(dto.getHorarioEntrega());
        pedido.setUsuario(usuario);
        pedido.setDireccion(direccion);
        pedido.setEstado(estado);

        return guardarPedidoConDetalles(pedido, dto.getDetalles());
    }

    @CacheEvict(value = "estadisticas", allEntries = true)
    private PedidoDTO crearPedidoAnonimo(PedidoDTO dto, String tokenClienteAnonimo) {

        ClienteAnonimoDTO datosCliente = dto.getClienteAnonimo();

        if (datosCliente.getNombre() == null || datosCliente.getNombre().isBlank()
                || datosCliente.getTelefono() == null || datosCliente.getTelefono().isBlank()
                || datosCliente.getCalle() == null || datosCliente.getCalle().isBlank()
                || datosCliente.getNumeroCasa() == null || datosCliente.getNumeroCasa().isBlank()) {
            throw new RuntimeException("Faltan datos del cliente anónimo");
        }

        ClienteAnonimo clienteAnonimo = new ClienteAnonimo();
        clienteAnonimo.setNombre(datosCliente.getNombre());
        clienteAnonimo.setTelefono(datosCliente.getTelefono());
        clienteAnonimo.setCalle(datosCliente.getCalle());
        clienteAnonimo.setNumeroCasa(datosCliente.getNumeroCasa());
        clienteAnonimo.setReferencia(datosCliente.getReferencia());
        clienteAnonimo.setToken(tokenClienteAnonimo);

        Estado estado = estadoRepository.findById(dto.getIdEstado())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        Pedido pedido = new Pedido();
        pedido.setFechaPedido(LocalDate.now());
        pedido.setFechaEntrega(dto.getFechaEntrega());
        pedido.setHorarioEntrega(dto.getHorarioEntrega());
        pedido.setClienteAnonimo(clienteAnonimo);
        pedido.setEstado(estado);

        return guardarPedidoConDetalles(pedido, dto.getDetalles());
    }

    private PedidoDTO guardarPedidoConDetalles(Pedido pedido, List<DetalleDTO> detallesDTO) {

        List<DetallePedido> detalles = new ArrayList<>();
        float total = 0;

        for (DetalleDTO detalleDTO : detallesDTO) {

            Presentacion presentacion = presentacionRepository.findById(detalleDTO.getIdPresentacion())
                    .orElseThrow(() -> new RuntimeException("Presentación no encontrada"));

            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setPresentacion(presentacion);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setTipoUso(detalleDTO.getTipoUso());

            float subtotal = presentacion.getPrecio() * detalleDTO.getCantidad();

            detalle.setSubtotal(subtotal);

            total += subtotal;

            detalles.add(detalle);
        }

        pedido.setDetallePedidos(detalles);
        pedido.setPrecioTotal(total);

        Pedido guardado = pedidoRepository.save(pedido);

        return mapeo(guardado);
    }

    //Listar pedidos
    public List<PedidoDTO> listarPedidos() {
        return pedidoRepository.findAll()
                .stream()
                .map(this::mapeoListarPedido)
                .toList();
    }

    //Borrar pedido
    public void borrarPedido(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        pedidoRepository.delete(pedido);
    }

    public List<PedidoDTO> listarPedidosPorCliente(Long idUsuario) {
        return pedidoRepository.findByUsuarioId(idUsuario)
                .stream()
                .map(this::mapeoListarPedido)
                .toList();
    }

    //Actualizar
    public PedidoDTO actualizarPedido(Long idPedido, PedidoDTO dto) {

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        Direccion direccion = direccionRepository.findById(dto.getIdDireccion())
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));

        Estado estado = estadoRepository.findById(dto.getIdEstado())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        pedido.setFechaEntrega(dto.getFechaEntrega());
        pedido.setHorarioEntrega(dto.getHorarioEntrega());
        pedido.setDireccion(direccion);
        pedido.setEstado(estado);

        Pedido actualizado = pedidoRepository.save(pedido);

        return mapeo(actualizado);
    }

    //Actualizar estado pedido
    public PedidoDTO actualizarEstadoPedido(Long idPedido, PedidoDTO dto) {

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));


        Estado estado = estadoRepository.findById(dto.getIdEstado())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        pedido.setEstado(estado);

        Pedido actualizado = pedidoRepository.save(pedido);

        return mapeo(actualizado);
    }


    private PedidoDTO mapeo(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();

        dto.setIdPedido(pedido.getId());
        dto.setFechaPedido(pedido.getFechaPedido());
        dto.setFechaEntrega(pedido.getFechaEntrega());
        dto.setHorarioEntrega(pedido.getHorarioEntrega());
        dto.setPrecioTotal(pedido.getPrecioTotal());

        if (pedido.getUsuario() != null) {
            dto.setIdUsuario(pedido.getUsuario().getId());
        }

        if (pedido.getDireccion() != null) {
            dto.setIdDireccion(pedido.getDireccion().getId());
        }

        if (pedido.getEstado() != null) {
            dto.setIdEstado(pedido.getEstado().getId());
        }

        return dto;
    }

    private PedidoDTO mapeoListarPedido(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();

        dto.setIdPedido(pedido.getId());
        dto.setFechaPedido(pedido.getFechaPedido());
        dto.setFechaEntrega(pedido.getFechaEntrega());
        dto.setHorarioEntrega(pedido.getHorarioEntrega());
        dto.setPrecioTotal(pedido.getPrecioTotal());

        // Cliente
        if (pedido.getUsuario() != null) {
            dto.setNombreCliente(
                    pedido.getUsuario().getNombre()
            );
            dto.setTelefonoCliente(pedido.getUsuario().getTelefono());
        } else if (pedido.getClienteAnonimo() != null) {
            dto.setNombreCliente(pedido.getClienteAnonimo().getNombre());
            dto.setTelefonoCliente(pedido.getClienteAnonimo().getTelefono());
            dto.setCalle(pedido.getClienteAnonimo().getCalle());
            try {
                dto.setNumeroCasa(
                        Integer.parseInt(pedido.getClienteAnonimo().getNumeroCasa())
                );
            } catch (NumberFormatException ignored) {
            }
            dto.setReferencia(pedido.getClienteAnonimo().getReferencia());
        }

        // Dirección
        if (pedido.getDireccion() != null) {
            dto.setCalle(
                    pedido.getDireccion().getCalle()
            );
            dto.setNumeroCasa(
                    pedido.getDireccion().getNumeroCasa()
            );
            dto.setReferencia(
                    pedido.getDireccion().getReferencia()
            );
        }

        // Estado
        if (pedido.getEstado() != null) {
            dto.setEstado(
                    pedido.getEstado().getEstado()
            );
            dto.setIdEstado(
                    pedido.getEstado().getId()
            );
        }

        // Detalles
        if (pedido.getDetallePedidos() != null) {

            List<DetalleDTO> detallesDTO = pedido.getDetallePedidos()
                    .stream()
                    .map(detalle -> {

                        DetalleDTO detalleDTO = new DetalleDTO();

                        detalleDTO.setIdDetalle(detalle.getId());
                        detalleDTO.setCantidad(detalle.getCantidad());
                        detalleDTO.setSubtotal(detalle.getSubtotal());
                        detalleDTO.setTipoUso(detalle.getTipoUso());

                        // Presentación
                        if (detalle.getPresentacion() != null) {

                            detalleDTO.setIdPresentacion(
                                    detalle.getPresentacion().getId()
                            );

                            detalleDTO.setNombrePresentacion(
                                    detalle.getPresentacion().getDescripcion()
                            );
                            detalleDTO.setCantidadPresentacion(
                                    detalle.getPresentacion().getCantidad()
                            );

                            detalleDTO.setSubtotal(
                                    detalle.getSubtotal()
                            );

                            // Producto
                            if (detalle.getPresentacion().getProducto() != null) {

                                detalleDTO.setNombreProducto(
                                        detalle.getPresentacion()
                                                .getProducto()
                                                .getNombre()
                                );
                            }
                        }

                        return detalleDTO;
                    })
                    .toList();

            dto.setDetalles(detallesDTO);
        }

        return dto;
    }


    @Cacheable("estadisticas")
    public EstadisticasDTO obtenerEstadisticas() {

        EstadisticasDTO estadisticas = new EstadisticasDTO();

        estadisticas.setVentasMensuales(obtenerVentasMensuales());
        estadisticas.setProductosMasVendidos(obtenerProductosMasVendidos());
        estadisticas.setClientesTop(obtenerClientesTop());
        estadisticas.setComparacionVentas(obtenerComparacionVentas());
        estadisticas.setVentasPorProductoMes(obtenerVentasPorProductoMes());

        return estadisticas;
    }

    private List<EstadisticasDTO.VentaMensualDTO> obtenerVentasMensuales() {
        List<Pedido> pedidos = pedidoRepository.findAll();

        Map<String, Double> ventasPorMes = pedidos.stream()
                .filter(p -> p.getFechaPedido() != null)
                .collect(Collectors.groupingBy(
                        p -> YearMonth.from(p.getFechaPedido()).toString(),
                        Collectors.summingDouble(Pedido::getPrecioTotal)
                ));

        List<EstadisticasDTO.VentaMensualDTO> resultado = new ArrayList<>();
        for (Map.Entry<String, Double> entry : ventasPorMes.entrySet()) {
            resultado.add(new EstadisticasDTO.VentaMensualDTO(entry.getKey(), entry.getValue()));
        }

        return resultado.stream()
                .sorted(Comparator.comparing(EstadisticasDTO.VentaMensualDTO::getMes))
                .toList();
    }

    private List<EstadisticasDTO.ProductoMasVendidoDTO> obtenerProductosMasVendidos() {

        List<Pedido> pedidos = pedidoRepository.findAll();
        Map<String, Integer> cantidadPorProducto = new HashMap<>();
        Map<String, Double> ventasPorProducto = new HashMap<>();

        for (Pedido pedido : pedidos) {
            if (pedido.getDetallePedidos() != null) {
                for (DetallePedido detalle : pedido.getDetallePedidos()) {

                    if (detalle.getPresentacion() != null &&
                            detalle.getPresentacion().getProducto() != null) {

                        String nombreProducto =
                                detalle.getPresentacion().getProducto().getNombre();

                        cantidadPorProducto.put(
                                nombreProducto,
                                cantidadPorProducto.getOrDefault(nombreProducto, 0)
                                        + detalle.getCantidad()
                        );

                        ventasPorProducto.put(
                                nombreProducto,
                                ventasPorProducto.getOrDefault(nombreProducto, 0.0)
                                        + detalle.getSubtotal()
                        );
                    }
                }
            }
        }

        return cantidadPorProducto.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(10)
                .map(entry -> new EstadisticasDTO.ProductoMasVendidoDTO(
                        entry.getKey(),
                        entry.getValue(),
                        ventasPorProducto.getOrDefault(entry.getKey(), 0.0)
                ))
                .toList();
    }

    private List<EstadisticasDTO.ClienteTopDTO> obtenerClientesTop() {
        List<Pedido> pedidos = pedidoRepository.findAll();

        Map<String, Integer> pedidosPorCliente = new HashMap<>();
        Map<String, Double> gastadoPorCliente = new HashMap<>();
        Map<String, String> nombrePorTelefono = new HashMap<>();

        for (Pedido pedido : pedidos) {

            String telefono;
            String nombre;

            if (pedido.getUsuario() != null) {
                telefono = pedido.getUsuario().getTelefono();
                nombre = pedido.getUsuario().getNombre();
            } else if (pedido.getClienteAnonimo() != null) {
                telefono = pedido.getClienteAnonimo().getTelefono();
                nombre = pedido.getClienteAnonimo().getNombre();
            } else {
                continue;
            }

            nombrePorTelefono.put(telefono, nombre);

            pedidosPorCliente.put(
                    telefono,
                    pedidosPorCliente.getOrDefault(telefono, 0) + 1
            );

            gastadoPorCliente.put(
                    telefono,
                    gastadoPorCliente.getOrDefault(telefono, 0.0) + pedido.getPrecioTotal()
            );
        }

        return pedidosPorCliente.keySet().stream()
                .map(telefono -> new EstadisticasDTO.ClienteTopDTO(
                        nombrePorTelefono.get(telefono),
                        pedidosPorCliente.get(telefono),
                        gastadoPorCliente.get(telefono),
                        telefono
                ))
                .sorted((a, b) -> Double.compare(
                        b.getTotalGastado(),
                        a.getTotalGastado()
                ))
                .limit(10)
                .toList();
    }

    private List<EstadisticasDTO.ComparacionVentasDTO> obtenerComparacionVentas() {
        List<Pedido> pedidos = pedidoRepository.findAll();

        Map<String, Double> ventasPorMes = pedidos.stream()
                .filter(p -> p.getFechaPedido() != null)
                .collect(Collectors.groupingBy(
                        p -> YearMonth.from(p.getFechaPedido()).toString(),
                        Collectors.summingDouble(Pedido::getPrecioTotal)
                ));

        Map<String, Long> pedidosPorMes = pedidos.stream()
                .filter(p -> p.getFechaPedido() != null)
                .collect(Collectors.groupingBy(
                        p -> YearMonth.from(p.getFechaPedido()).toString(),
                        Collectors.counting()
                ));

        List<EstadisticasDTO.ComparacionVentasDTO> resultado = new ArrayList<>();
        for (String mes : ventasPorMes.keySet()) {
            resultado.add(new EstadisticasDTO.ComparacionVentasDTO(
                    mes,
                    ventasPorMes.get(mes),
                    pedidosPorMes.get(mes).intValue()
            ));
        }

        return resultado.stream()
                .sorted(Comparator.comparing(EstadisticasDTO.ComparacionVentasDTO::getMes))
                .collect(Collectors.toList());
    }

    private List<EstadisticasDTO.VentaProductoMesDTO> obtenerVentasPorProductoMes() {

        List<Pedido> pedidos = pedidoRepository.findAll();

        Map<String, Double> kilosPorProductoMes = new HashMap<>();

        for (Pedido pedido : pedidos) {

            if (pedido.getFechaPedido() == null || pedido.getDetallePedidos() == null) {
                continue;
            }

            String mes = YearMonth.from(pedido.getFechaPedido()).toString();

            for (DetallePedido detalle : pedido.getDetallePedidos()) {

                if (detalle.getPresentacion() == null ||
                        detalle.getPresentacion().getProducto() == null) {
                    continue;
                }

                String producto = detalle
                        .getPresentacion()
                        .getProducto()
                        .getNombre();

                double kilosVendidos =
                        detalle.getCantidad()
                                * detalle.getPresentacion().getCantidad();

                String clave = mes + "|" + producto;

                kilosPorProductoMes.put(
                        clave,
                        kilosPorProductoMes.getOrDefault(clave, 0.0)
                                + kilosVendidos
                );
            }
        }

        List<EstadisticasDTO.VentaProductoMesDTO> resultado = new ArrayList<>();

        for (Map.Entry<String, Double> entry : kilosPorProductoMes.entrySet()) {

            String[] partes = entry.getKey().split("\\|");

            resultado.add(
                    new EstadisticasDTO.VentaProductoMesDTO(
                            partes[0], // mes
                            partes[1], // producto
                            entry.getValue() // kilos vendidos
                    )
            );
        }

        return resultado.stream()
                .sorted(Comparator.comparing(
                        EstadisticasDTO.VentaProductoMesDTO::getMes))
                .toList();
    }
}
