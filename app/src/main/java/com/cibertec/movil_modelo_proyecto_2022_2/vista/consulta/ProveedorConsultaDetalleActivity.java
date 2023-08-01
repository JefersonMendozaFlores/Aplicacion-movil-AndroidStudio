package com.cibertec.movil_modelo_proyecto_2022_2.vista.consulta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cibertec.movil_modelo_proyecto_2022_2.R;
import com.cibertec.movil_modelo_proyecto_2022_2.adapter.ProveedorAdapter;
import com.cibertec.movil_modelo_proyecto_2022_2.entity.Proveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.service.ServiceProveedor;
import com.cibertec.movil_modelo_proyecto_2022_2.util.ConnectionRest;
import com.cibertec.movil_modelo_proyecto_2022_2.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProveedorConsultaDetalleActivity extends NewAppCompatActivity {


    TextView txtDetalleIdProveedorConsulta, txtDetalleRazonSocialProveedorConsulta;
    TextView txtDetalleRucProveedorConsulta, txtDetalleDireProveedorConsulta, txtDetalleTelefProveedorConsulta,
            txtDetalleCelProveedorConsulta,txtDetalleContactoProveedorConsulta, txtDetalleEstadoProveedorConsulta,
            txtDetalleFechaRegProveedorConsulta, txtDetallePaisProveedorConsulta, txtDetalleTipoProveedorConsulta;

    Button btnRegresar;












    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_consulta_detalle);






        txtDetalleIdProveedorConsulta = findViewById(R.id.txtDetalleIdProveedorConsulta);
        txtDetalleRazonSocialProveedorConsulta = findViewById(R.id.txtDetalleRazonSocialProveedorConsulta);
        txtDetalleRucProveedorConsulta = findViewById(R.id.txtDetalleRucProveedorConsulta);
        txtDetalleDireProveedorConsulta = findViewById(R.id.txtDetalleDireProveedorConsulta);
        txtDetalleTelefProveedorConsulta = findViewById(R.id.txtDetalleTelefProveedorConsulta);
        txtDetalleCelProveedorConsulta = findViewById(R.id.txtDetalleCelProveedorConsulta);
        txtDetalleContactoProveedorConsulta = findViewById(R.id.txtDetalleContactoProveedorConsulta);
        txtDetalleEstadoProveedorConsulta = findViewById(R.id.txtDetalleEstadoProveedorConsulta);
        txtDetalleFechaRegProveedorConsulta = findViewById(R.id.txtDetalleFechaRegProveedorConsulta);
        txtDetallePaisProveedorConsulta = findViewById(R.id.txtDetallePaisProveedorConsulta);
        txtDetalleTipoProveedorConsulta = findViewById(R.id.txtDetalleTipoProveedorConsulta);
        btnRegresar = findViewById(R.id.btnDetalleRegresar);


        Bundle extras =  getIntent().getExtras();
        Proveedor objProveedor = (Proveedor) extras.get("VAR_OBJETO");


        String idProveedorConsulta = String.valueOf(objProveedor.getIdProveedor());

        txtDetalleIdProveedorConsulta.setText("ID:" + idProveedorConsulta);
        txtDetalleRazonSocialProveedorConsulta.setText(objProveedor.getRazonsocial());
        txtDetalleRucProveedorConsulta.setText("RUC : " + objProveedor.getRuc());
        txtDetalleDireProveedorConsulta.setText("Direccion : " + objProveedor.getDireccion());
        txtDetalleTelefProveedorConsulta.setText("Telefono :" + objProveedor.getTelefono());
        txtDetalleCelProveedorConsulta.setText("Celular : " + objProveedor.getCelular());
        txtDetalleContactoProveedorConsulta.setText("Contacto : " + objProveedor.getContacto());
        txtDetalleEstadoProveedorConsulta.setText("Estado :" + objProveedor.getEstado());
        txtDetalleFechaRegProveedorConsulta.setText("Fecha Registro :" + objProveedor.getFechaRegistro());
        txtDetallePaisProveedorConsulta.setText("Pais : " + objProveedor.getPais().getNombre());
        txtDetalleTipoProveedorConsulta.setText("Tipo : " + objProveedor.getTipoProveedor().getDescripcion());









        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }





        });





    }









}