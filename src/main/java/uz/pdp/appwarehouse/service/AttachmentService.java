package uz.pdp.appwarehouse.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.appwarehouse.entity.Attachment;
import uz.pdp.appwarehouse.entity.AttachmentContent;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.AttachmentContentRepository;
import uz.pdp.appwarehouse.repository.AttachmentRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    @SneakyThrows
    public Result uploadFile(MultipartHttpServletRequest request) {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        Attachment attachment = new Attachment();
        attachment.setName(file.getOriginalFilename());
        attachment.setSize(file.getSize());
        attachment.setContentType(file.getContentType());
        Attachment savedAttachment = attachmentRepository.save(attachment);

        AttachmentContent attachmentContent = new AttachmentContent();
        attachmentContent.setBytes(file.getBytes());
        attachmentContent.setAttachment(savedAttachment);
        attachmentContentRepository.save(attachmentContent);

        return new Result("Fayl saqlandi", true, savedAttachment.getId());
    }

    public List<Attachment> getAttachmentList(){
        List<Attachment> attachmentList = attachmentRepository.findAll();
        return attachmentList;
    }

    public Attachment getAttachment(Integer attachmentId){
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(attachmentId);
        return optionalAttachment.orElse(null);
    }

    @SneakyThrows
    public Result editAttachment(Integer attachmentId, MultipartHttpServletRequest request){
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(attachmentId);
        if (optionalAttachment.isPresent()){
            Iterator<String> fileNames = request.getFileNames();
            MultipartFile file = request.getFile(fileNames.next());

            Attachment attachment = optionalAttachment.get();
            attachment.setName(file.getOriginalFilename());
            attachment.setSize(file.getSize());
            attachment.setContentType(file.getContentType());
            Attachment savedAttachment = attachmentRepository.save(attachment);

            AttachmentContent attachmentContent = new AttachmentContent();
            attachmentContent.setBytes(file.getBytes());
            attachmentContent.setAttachment(savedAttachment);
            attachmentContentRepository.save(attachmentContent);

            return new Result("muvaffaqiyatli ozgartirildi", true);
        }
        return new Result("Bunday rasm mavjud emas", false);
    }

    public Result deleteAttachment(Integer attachmentId){
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(attachmentId);
        if (optionalAttachment.isPresent()){
            attachmentRepository.deleteById(attachmentId);
            return new Result("muvaffaqiyatli ochirildi", true);
        }
        return new Result("Bunday rasm mavjud emas", false);
    }
}
