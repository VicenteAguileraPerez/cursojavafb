package Notificaciones;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class Notificationes
{
    public void Notificacion(String titulo,String message,int opc)
    {
        Notifications notifications = Notifications.create()
                .title(titulo)
                .text(message)
                .hideAfter(Duration.seconds(3))
                .position(Pos.BASELINE_RIGHT);

        switch(opc)
        {
            case 1:
                notifications.showConfirm();
                break;
            case 2:
                notifications.showError();
                break;
            default:
                notifications.showInformation();
                break;

        }

    }
}
