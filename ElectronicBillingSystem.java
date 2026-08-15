interface FormatExporter {
    void export(String content);
}

class XMLExporter implements FormatExporter {
    @Override
    public void export(String content) {
        System.out.println("[XML] Exporting: " + content);
    }
}

class PDFExporter implements FormatExporter {
    @Override
    public void export(String content) {
        System.out.println("[PDF] Exporting: " + content);
    }
}

class JSONExporter implements FormatExporter {
    @Override
    public void export(String content) {
        System.out.println("[JSON] Exporting: " + content);
    }
}

class EDIExporter implements FormatExporter {
    @Override
    public void export(String content) {
        System.out.println("[EDI] Exporting: " + content);
    }
}

interface DeliveryChannel {
    void send(String content);
}

class EmailDelivery implements DeliveryChannel {
    @Override
    public void send(String content) {
        System.out.println("[Email] Sending: " + content);
    }
}

class WhatsAppDelivery implements DeliveryChannel {
    @Override
    public void send(String content) {
        System.out.println("[WhatsApp] Sending: " + content);
    }
}

class WebPortalDelivery implements DeliveryChannel {
    @Override
    public void send(String content) {
        System.out.println("[Web Portal] Sending: " + content);
    }
}

interface SignatureProvider {
    void sign(String content);
}

class LocalSignature implements SignatureProvider {
    @Override
    public void sign(String content) {
        System.out.println("[Local] Signing: " + content);
    }
}

class CloudSignature implements SignatureProvider {
    @Override
    public void sign(String content) {
        System.out.println("[Cloud] Signing: " + content);
    }
}

class HSMSignature implements SignatureProvider {
    @Override
    public void sign(String content) {
        System.out.println("[HSM] Signing: " + content);
    }
}

abstract class Voucher {
    protected FormatExporter formatExporter;
    protected DeliveryChannel deliveryChannel;
    protected SignatureProvider signatureProvider;

    public Voucher(FormatExporter formatExporter, DeliveryChannel deliveryChannel, SignatureProvider signatureProvider) {
        this.formatExporter = formatExporter;
        this.deliveryChannel = deliveryChannel;
        this.signatureProvider = signatureProvider;
    }

    protected abstract String buildContent();

    public void process() {
        String content = buildContent();
        formatExporter.export(content);
        deliveryChannel.send(content);
        signatureProvider.sign(content);
    }
}

class SalesInvoice extends Voucher {
    public SalesInvoice(FormatExporter formatExporter, DeliveryChannel deliveryChannel, SignatureProvider signatureProvider) {
        super(formatExporter, deliveryChannel, signatureProvider);
    }

    @Override
    protected String buildContent() {
        return "SalesInvoice #001 - Total: $150.000";
    }
}

class CreditNote extends Voucher {
    public CreditNote(FormatExporter formatExporter, DeliveryChannel deliveryChannel, SignatureProvider signatureProvider) {
        super(formatExporter, deliveryChannel, signatureProvider);
    }

    @Override
    protected String buildContent() {
        return "CreditNote #CN-42 - Refund: $5.000";
    }
}

class DebitNote extends Voucher {
    public DebitNote(FormatExporter formatExporter, DeliveryChannel deliveryChannel, SignatureProvider signatureProvider) {
        super(formatExporter, deliveryChannel, signatureProvider);
    }

    @Override
    protected String buildContent() {
        return "DebitNote #DN-88 - Charge: $2.000";
    }
}

class WithholdingVoucher extends Voucher {
    public WithholdingVoucher(FormatExporter formatExporter, DeliveryChannel deliveryChannel, SignatureProvider signatureProvider) {
        super(formatExporter, deliveryChannel, signatureProvider);
    }

    @Override
    protected String buildContent() {
        return "WithholdingVoucher #WH-09 - Amount: $1.500";
    }
}

public class ElectronicBillingSystem {
    public static void main(String[] args) {
        Voucher invoice = new SalesInvoice(new PDFExporter(), new EmailDelivery(), new CloudSignature());
        invoice.process();

        Voucher creditNote = new CreditNote(new XMLExporter(), new WebPortalDelivery(), new LocalSignature());
        creditNote.process();

        Voucher debitNote = new DebitNote(new JSONExporter(), new WhatsAppDelivery(), new HSMSignature());
        debitNote.process();

        Voucher withholding = new WithholdingVoucher(new PDFExporter(), new WebPortalDelivery(), new HSMSignature());
        withholding.process();

        Voucher ediInvoice = new SalesInvoice(new EDIExporter(), new WhatsAppDelivery(), new CloudSignature());
        ediInvoice.process();
    }
}
