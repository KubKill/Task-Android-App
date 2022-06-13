package pl.edu.pja.mp1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import pl.edu.pja.mp1.databinding.PieDiagramFragmentBinding

class PieDiagramFragment : Fragment(){

    private lateinit var  binding: PieDiagramFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return PieDiagramFragmentBinding.inflate(
            inflater, container, false
        ).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun drawPieDiagram(progress: Int){
        binding.pieDiagramView.setProgressValue(progress)
    }
}