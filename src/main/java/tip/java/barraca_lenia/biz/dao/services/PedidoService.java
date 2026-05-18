package tip.java.barraca_lenia.biz.dao.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tip.java.barraca_lenia.biz.dao.entities.*;
import tip.java.barraca_lenia.biz.dao.repositories.*;
import tip.java.barraca_lenia.dto.DetalleDTO;
import tip.java.barraca_lenia.dto.PedidoDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final DireccionRepository direccionRepository;
    private final EstadoRepository estadoRepository;
    private final PresentacionRepository presentacionRepository;


    //crear pedido
    public PedidoDTO crearPedido(PedidoDTO dto) {

        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Direccion direccion = direccionRepository.findById(dto.getIdDireccion())
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));

        Estado estado = estadoRepository.findById(dto.getIdEstado())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        Pedido pedido = new Pedido();
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setFechaEntrega(dto.getFechaEntrega());
        pedido.setHorarioEntrega(dto.getHorarioEntrega());
        pedido.setUsuario(usuario);
        pedido.setDireccion(direccion);
        pedido.setEstado(estado);

        List<DetallePedido> detalles = new ArrayList<>();
        float total = 0;

        for (DetalleDTO detalleDTO : dto.getDetalles()) {

            Presentacion presentacion = presentacionRepository.findById(detalleDTO.getIdPresentacion())
                    .orElseThrow(() -> new RuntimeException("Presentación no encontrada"));

            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setPresentacion(presentacion);
            detalle.setCantidad(detalleDTO.getCantidad());

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

        dto.setIdUsuario(pedido.getUsuario().getId());
        dto.setIdDireccion(pedido.getDireccion().getId());
        dto.setIdEstado(pedido.getEstado().getId());

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

                        // Presentación
                        if (detalle.getPresentacion() != null) {

                            detalleDTO.setIdPresentacion(
                                    detalle.getPresentacion().getId()
                            );

                            detalleDTO.setNombrePresentacion(
                                    detalle.getPresentacion().getDescripcion()
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


}
